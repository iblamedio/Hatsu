using Application.Interfaces;

namespace Application.UseCases.EntryUseCases.DeleteEntryUseCase;

public class DeleteEntryUseCase(IEntryRepository repo, IPlayerRepository repoPlayer)
{
    public async Task ExecuteAsync(Guid entryId, Guid playerId)
    {
        var entry = await repo.GetByIdAsync(entryId);
        if (entry is null)
        {
            throw new InvalidOperationException($"Entry with id {entryId} not found");
        }
        
        var player = await repoPlayer.GetByIdAsync(playerId);
        if (player is null)
        {
            throw new InvalidOperationException($"Player with id {playerId} not found");
        }

        if (entry.PlayerId != playerId)
        {
            throw new  InvalidOperationException("Entry does not belong to player");
        }

        await repo.DeleteAsync(entry.Id);
        await repo.SaveAsync();
    }
}