namespace Application.Interfaces;

public interface IJwtService
{
    public string GenerateJwtToken(Guid playerId);
}