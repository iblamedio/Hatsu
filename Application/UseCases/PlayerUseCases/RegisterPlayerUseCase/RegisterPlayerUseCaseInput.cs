namespace Application.UseCases.PlayerUseCases.RegisterPlayerUseCase;

public class RegisterPlayerUseCaseInput(string username, string password)
{
    public string Username { get; init; } = username;
    public string Password { get; init; } = password;
}