using System.Diagnostics;
using Domain.Enums;

namespace Domain.Entities;

public class Entry : Entity<Guid>
{
    
    private Entry() {}
    
    private Entry(Game game, Guid playerId, Status? status, int? score)
    {
        Id = Guid.NewGuid();
        Game = game;
        PlayerId = playerId;
        Status = status ?? Status.Planning;
        Score = score;
        CreatedAt = DateTime.UtcNow;
    }

    public Game Game { get; init; }
    public Guid PlayerId { get; init; }
    public int? Score { get; private set; }
    public Status Status { get; private set; }

    public void ChangeScore(int? newScore)
    {
        if (newScore is 0 or > 100)
        {
            throw new ArgumentOutOfRangeException(nameof(newScore), "Score must be between 0 and 100");
        }
        
        Score = newScore;
        UpdatedAt = DateTime.UtcNow;
    }

    public void ChangeStatus(Status? status)
    {
        if (status is null)
        {
            Status = Status.Planning;
            UpdatedAt = DateTime.UtcNow;
            return;
        }
        
        Status = (Status) status!;
        UpdatedAt = DateTime.UtcNow;
    }

    public static Entry Create(Game game, Guid playerId, Status? status, int? score)
    {
        if (score is < 0 or > 100)
        {
            throw new ArgumentOutOfRangeException(nameof(score));
        }

        return new Entry(game, playerId, status ?? Enums.Status.Planning, score);
    }
}