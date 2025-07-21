using Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace Persistence;

public class HatsuDbContext(DbContextOptions<HatsuDbContext> options) : DbContext(options)
{
    public DbSet<Player> Players => Set<Player>();
    public DbSet<Entry> Entries => Set<Entry>();
    public DbSet<Game> Games => Set<Game>();
}