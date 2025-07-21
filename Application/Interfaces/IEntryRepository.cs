using Domain.Entities;

namespace Application.Interfaces;

public interface IEntryRepository
{
    Task<Entry?> GetByUserIdAndGameId(Guid userId, long gameId);
    Task<IEnumerable<Entry>> GetAllByUserAsync(Player player);
    
    Task AddAsync(Entry entry);
    Task SaveAsync();
}