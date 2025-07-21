namespace Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;

public class RegisterPlayerUseCaseOutput(string token, int expiresIn)
{
    public string Token { get; init; } = token;
    public int ExpiresIn { get; init; } = expiresIn;
}