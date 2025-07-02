namespace Domain.Entities;

public sealed class Game : Entity<long>
{
    private Game(long id, string title, string summary, string coverUrl)
    {
        Id = id;
        Title = title;
        Summary = summary;
        CoverUrl = coverUrl;
        CreatedAt = DateTimeOffset.UtcNow;
    }
    
    public string Title { get; init; }
    public string Summary { get; init; }
    public string CoverUrl { get; init; }

    public static Game Create(long id, string title, string summary, string coverUrl)
    {
        if (title.Trim() == "")
        {
            throw new ArgumentException("Title cannot be empty");
        }
        
        return new Game(id, title, summary, coverUrl);
    }
}