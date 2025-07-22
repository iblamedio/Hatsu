using Domain.Entities;
using Application.Interfaces;

namespace Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;

public class RegisterPlayerUseCase(IJwtService jwtService, IPlayerRepository repo, IPasswordHasher passwordHasher)
{
    public async Task<RegisterPlayerUseCaseOutput> ExecuteAsync(RegisterPlayerUseCaseInput input)
    {
        var username  = input.Username.Trim();
        var password  = input.Password.Trim();

        if (username.Contains(' '))
        {
            throw new InvalidOperationException("Username cannot contain spaces");
        }

        if (username.Length > 20)
        {
            throw new InvalidOperationException("Username is too long");
        }
        
        if (username.Length <= 3)
        {
            throw new InvalidOperationException("Username is too short");
        }
        if (password.Length < 6)
        {
            throw new InvalidOperationException("Password is too short");
        } 
        
        var existing = await repo.GetByUsername(username);
        if (existing is not null)
        {
            throw new InvalidOperationException("Username already taken");
        }
        
        var hashedPassword = passwordHasher.HashPassword(password);
        
        var newPlayer = Player.Create(username, hashedPassword);

        await repo.AddAsync(newPlayer);
        await repo.SaveAsync();
        
        var token = jwtService.GenerateJwtToken(newPlayer.Id);

        // TODO review ExpiresIn
        return new RegisterPlayerUseCaseOutput(token, 20000);
    }
}