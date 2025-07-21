using Domain.Entities;

namespace Application.Interfaces;

public interface IEntryRepository
{
    Task<Entry?> GetByIdAsync(Guid entryId);
    Task<Entry?> GetByUserIdAndGameId(Guid userId, long gameId);
    Task<IEnumerable<Entry>> GetAllByUserAsync(Player player);
    
    Task UpdateAsync(Entry entry);
    
    Task DeleteAsync(Guid entryId);
    
    Task AddAsync(Entry entry);
    Task SaveAsync();
}