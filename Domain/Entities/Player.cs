namespace Domain.Entities;

public class Player : Entity<Guid>
{
    private Player(string username, string hashedPassword)
    {
        Id = Guid.NewGuid();
        Username = username;
        HashedPassword = hashedPassword;
    } 
    
    public Guid Id { get; init; }
    public string Username { get; private set; }
    public string HashedPassword { get; private set; }

    public static Player Create(string username, string hashedPassword)
    {
        if (username.Length <= 3)
        {
            throw new ArgumentException("Username must be at least 3 characters");
        }

        return new Player(username, hashedPassword);
    }
}