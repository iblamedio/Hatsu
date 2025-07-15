using Domain.Entities;
using Domain.Interfaces;
using Domain.ValueObjects;

namespace Application.UseCases.GetEntriesByUser;

public class GetEntriesByUserUseCase(IEntryRepository entryRepository)
{
    public async Task<Playlists> ExecuteAsync(User user)
    {
        var entries = await entryRepository.GetAllByUserAsync(user);

        return Playlists.FromEntries(entries);
    }

}