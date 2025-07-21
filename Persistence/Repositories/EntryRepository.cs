using Application.Interfaces;
using Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace Persistence.Repositories;

public class EntryRepository(HatsuDbContext context) : IEntryRepository
{
    public async Task<Entry?> GetByIdAsync(Guid entryId)
    {
        return await context.Entries.FindAsync(entryId);
    }

    public async Task<Entry?> GetByUserIdAndGameId(Guid userId, long gameId)
    {
        return await context.Entries.FindAsync(userId, gameId);
    }

    public async Task<IEnumerable<Entry>> GetAllByUserAsync(Player player)
    {
        return await context.Entries.AsNoTracking().Where(e => e.PlayerId == player.Id).ToListAsync();
    }

    public async Task UpdateAsync(Entry entry)
    {
        context.Entries.Update(entry);
        await context.SaveChangesAsync();
    }

    public async Task DeleteAsync(Guid entryId)
    {
        var entry = await context.Entries.FindAsync(entryId);
        if (entry is null)
        {
            throw new NullReferenceException("Entry not found");
        }
        
        context.Entries.Remove(entry);
        await context.SaveChangesAsync();
    }

    public async Task AddAsync(Entry entry)
    {
        await context.Entries.AddAsync(entry);
    }

    public async Task SaveAsync()
    {
        await context.SaveChangesAsync();
    }
}