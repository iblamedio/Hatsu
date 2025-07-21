using Application.Interfaces;

namespace Application.UseCases.EntryUseCases.UpdateEntryUseCase;

public abstract class UpdateEntryUseCase(IEntryRepository repo, IPlayerRepository repoPlayer)
{
    public async Task ExecuteAsync(UpdateEntryUseCaseInput input)
    {
        var player = await repoPlayer.GetByIdAsync(input.PlayerId);
        if (player is null)
        {
            throw new InvalidOperationException("Player not found");
        }
        
        var entry = await repo.GetByIdAsync(input.EntryId);
        if (entry is null)
        {
            throw new InvalidOperationException("Entry not found");
        }
        
        if (entry.PlayerId != player.Id)
        {
            throw new InvalidOperationException("Entry does not belong to player");
        }

        if (input.Status != entry.Status)
        {
            entry.ChangeStatus(input.Status);
        }

        if (input.Score != entry.Score)
        {
            entry.ChangeScore(input.Score);
        }

        await repo.UpdateAsync(entry);

    }
}