using Application.Interfaces;

namespace Application.UseCases.PlayerUseCases.FetchPlayerDataUseCase;

public class FetchPlayerDataUseCase(IPlayerRepository repo)
{
    public async Task<FetchPlayerDataUseCaseOutput> ExecuteAsync(Guid playerId)
    {
        var player = await repo.GetByIdAsync(playerId);
        if (player == null) throw new InvalidOperationException("Player not found");

        return new FetchPlayerDataUseCaseOutput(player.Username);
    }
}