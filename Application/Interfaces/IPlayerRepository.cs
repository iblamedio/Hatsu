using Domain.Entities;

namespace Application.Interfaces;

public interface IPlayerRepository
{
    Task AddAsync(Player player);
    
    Task<Player?> GetByIdAsync(Guid playerId);
    Task<Player?> GetByUsername(string username);
    
    Task SaveAsync();
}