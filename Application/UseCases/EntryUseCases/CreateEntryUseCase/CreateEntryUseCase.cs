using Application.Interfaces;
using Domain.Entities;

namespace Application.UseCases.EntryUseCases.CreateEntryUseCase;

public class CreateEntryUseCase(IEntryRepository repo, IGameRepository repoGame)
{
    public async Task<Entry> ExecuteAsync(CreateEntryUseCaseInput input)
    {
        var game = await repoGame.GetByIdAsync(input.GameId);
        
        if (game is null)
        {
            game = await repoGame.FetchGameAsync(input.GameId);
        }
        
        if (game is null)
        {
            throw new InvalidOperationException($"Game with id {input.GameId} not found");
        }
        
        var existing = await repo.GetByUserIdAndGameId(input.PlayerId, input.GameId);
        if (existing is not null)
        {
            throw new InvalidOperationException("Entry already exists");
        }

        var newEntry = Entry.Create(game, input.PlayerId, input.Status, input.Score);
        
        await repo.AddAsync(newEntry);
        await repo.SaveAsync();

        return newEntry;
    }
}