using System.Text;
using Api.Middleware;
using Application.Interfaces;
using Application.UseCases.EntryUseCases.CreateEntryUseCase;
using Application.UseCases.EntryUseCases.DeleteEntryUseCase;
using Application.UseCases.EntryUseCases.GetEntriesByUser;
using Application.UseCases.EntryUseCases.UpdateEntryUseCase;
using Application.UseCases.PlayerUseCases.LoginUseCase;
using Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Persistence;
using Persistence.Repositories;
using Services;

var builder = WebApplication.CreateBuilder(args);

var jwtSettings = builder.Configuration.GetSection("Jwt");
var key = Encoding.UTF8.GetBytes(jwtSettings["Key"]!);

builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
}).AddJwtBearer(options =>
{
    options.TokenValidationParameters =
        new TokenValidationParameters
        {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(key),
            ValidateIssuer = true,
            ValidateAudience = true,
            ValidAudience = jwtSettings["Audience"]!,
            ValidIssuer = jwtSettings["Issuer"]!,
        };
});

builder.Services.AddDbContext<HatsuDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<IEntryRepository, EntryRepository>();
builder.Services.AddScoped<IGameRepository, GameRepository>();
builder.Services.AddScoped<IPlayerRepository, PlayerRepository>();

builder.Services.AddScoped<IJwtService, JwtService>();
builder.Services.AddScoped<IPasswordHasher, BCryptPasswordHasher>();

builder.Services.AddScoped<CreateEntryUseCase>();
builder.Services.AddScoped<GetEntriesByUserUseCase>();
builder.Services.AddScoped<UpdateEntryUseCase>();
builder.Services.AddScoped<DeleteEntryUseCase>();

builder.Services.AddScoped<RegisterPlayerUseCase>();
builder.Services.AddScoped<LoginUseCase>();

builder.Services.AddControllers();

var app = builder.Build();

app.UseAuthentication();
app.UseAuthorization();

app.UseMiddleware<ErrorHandler>();

app.MapControllers();

app.Run();