using Domain.Entities;
using Application.Interfaces;

namespace Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;

public class RegisterPlayerUseCase(IJwtService jwtService, IPlayerRepository repo, IPasswordHasher passwordHasher)
{
    public async Task<RegisterPlayerUseCaseOutput> ExecuteAsync(RegisterPlayerUseCaseInput input)
    {
        var existing = await repo.GetByUsername(input.Username);
        if (existing is not null)
        {
            throw new InvalidOperationException("Username already exists");
        }
        
        if (input.Password.Length < 6)
        {
            throw new InvalidOperationException("Password is too short");
        } 
        
        var hashedPassword = passwordHasher.HashPassword(input.Password);
        
        var newPlayer = Player.Create(input.Username, hashedPassword);

        await repo.AddAsync(newPlayer);
        await repo.SaveAsync();
        
        var token = jwtService.GenerateJwtToken(newPlayer.Id);

        // TODO review ExpiresIn
        return new RegisterPlayerUseCaseOutput(token, 20000);
    }
}