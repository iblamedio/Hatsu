using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.Configuration;

namespace Persistence;

public class DbFactory(IConfiguration config) : IDesignTimeDbContextFactory<HatsuDbContext>
{
    public HatsuDbContext CreateDbContext(string[] args)
    {
        var options = new DbContextOptionsBuilder<HatsuDbContext>();
        options.UseNpgsql(connectionString: config.GetConnectionString("DefaultConnection"));
        return new HatsuDbContext(options.Options);
    }
}