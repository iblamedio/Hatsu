using Domain.Entities;

namespace Domain.Interfaces;

public interface IEntryRepository
{
    Task<IEnumerable<Entry>> GetAllByUserAsync(User user);
}