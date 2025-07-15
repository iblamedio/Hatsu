namespace Domain.Entities;

public class User : Entity<Guid>
{
    public string Username { get; private set; }
    public string Password { get; private set; }
}