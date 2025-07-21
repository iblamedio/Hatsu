namespace Application.UseCases.PlayerUseCases.LoginUseCase;

public record LoginUseCaseOutput(string Token, int ExpiresIn)
{
}