using Application.Interfaces;

namespace Application.UseCases.PlayerUseCases.LoginUseCase;

public class LoginUseCase(IPlayerRepository repo, IPasswordHasher hasher, IJwtService jwtService)
{
    public async Task<LoginUseCaseOutput> ExecuteAsync(LoginUseCaseInput input)
    {
        var player = await repo.GetByUsername(input.Username);
        if (player is null)
        {
            throw new InvalidOperationException($"Player with username {input.Username} not found");
        }

        var passwordIsValid = hasher.VerifyHashedPassword(player.HashedPassword, input.Password);
        if (!passwordIsValid)
        {
            throw new InvalidOperationException("Invalid password");
        }

        var token = jwtService.GenerateJwtToken(player.Id);

        return new LoginUseCaseOutput(token, 20000);
    }
}