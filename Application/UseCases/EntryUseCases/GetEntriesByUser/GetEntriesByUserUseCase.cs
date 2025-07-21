using Application.Interfaces;
using Domain.Entities;
using Domain.ValueObjects;

namespace Application.UseCases.EntryUseCases.GetEntriesByUser;

public abstract class GetEntriesByUserUseCase(IEntryRepository repo)
{
    public async Task<Playlists> ExecuteAsync(Guid playerId)
    {
        var entries = await repo.GetAllByUserAsync(playerId);

        return Playlists.FromEntries(entries);
    }

}