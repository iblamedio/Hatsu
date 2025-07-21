using Application.Interfaces;
using Domain.Entities;
using Domain.ValueObjects;

namespace Application.UseCases.EntryUseCases.GetEntriesByUser;

public class GetEntriesByUserUseCase(IEntryRepository repo)
{
    public async Task<Playlists> ExecuteAsync(Player player)
    {
        var entries = await repo.GetAllByUserAsync(player);

        return Playlists.FromEntries(entries);
    }

}