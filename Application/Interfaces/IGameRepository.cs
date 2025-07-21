using Domain.Entities;

namespace Application.Interfaces;

public interface IGameRepository
{
    Task<Game?> GetByIdAsync(long id);
    Task<Game?> FetchGameAsync(long id);
}