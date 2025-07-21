using Application.Interfaces;
using Domain.Entities;

namespace Persistence.Repositories;

public class GameRepository(HatsuDbContext context) :  IGameRepository
{
    public async Task<Game?> GetByIdAsync(long id)
    {
        return await context.Games.FindAsync(id);
    }

    public async Task<Game?> FetchGameAsync(long id)
    {
        throw new NotImplementedException();
        await context.SaveChangesAsync();
    }
}