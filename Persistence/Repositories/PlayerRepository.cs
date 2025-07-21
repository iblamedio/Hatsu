using Application.Interfaces;
using Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace Persistence.Repositories;

public class PlayerRepository(HatsuDbContext context) : IPlayerRepository
{
    public async Task AddAsync(Player player)
    {
        await context.Players.AddAsync(player);
    }

    public async Task<Player?> GetByIdAsync(Guid playerId)
    {
        return await context.Players.FindAsync(playerId);
    }

    public async Task<Player?> GetByUsername(string username)
    {
        return await context.Players.AsNoTracking().FirstOrDefaultAsync(p => p.Username == username);
    }

    public Task SaveAsync()
    {
        return context.SaveChangesAsync();
    }
}