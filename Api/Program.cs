using Application.Interfaces;
using Application.UseCases.EntryUseCases.CreateEntryUseCase;
using Application.UseCases.EntryUseCases.DeleteEntryUseCase;
using Application.UseCases.EntryUseCases.GetEntriesByUser;
using Application.UseCases.EntryUseCases.UpdateEntryUseCase;
using Application.UseCases.PlayerUseCases.LoginUseCase;
using Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;
using Microsoft.EntityFrameworkCore;
using Persistence;
using Persistence.Repositories;
using Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<HatsuDbContext>(options => options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

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

app.MapControllers();

app.Run();