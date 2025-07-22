using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;

namespace Persistence;

public class DbFactory : IDesignTimeDbContextFactory<HatsuDbContext>
{
    public HatsuDbContext CreateDbContext(string[] args)
    {
        var options = new DbContextOptionsBuilder<HatsuDbContext>();
        options.UseNpgsql(connectionString: "Host=localhost;Port=5432;Username=postgres;Password=secret;Database=postgres");
        return new HatsuDbContext(options.Options);
    }
}