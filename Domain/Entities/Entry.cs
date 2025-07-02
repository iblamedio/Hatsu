using Domain.Enums;

namespace Domain.Entities;

public class Entry : Entity<Guid>
{
    private Entry(Game game, int playerId, Status? status, int? score)
    {
        Id = Guid.NewGuid();
        Game = game;
        PlayerId = playerId;
        Status = status ?? Status.Planning;
        Score = score;
        CreatedAt = DateTime.UtcNow;
    }

    public Game Game { get; init; }
    // TODO melhorar isso aq
    public int PlayerId { get; init; }
    public int? Score { get; private set; }
    public Status Status { get; private set; }

    public static Entry Create(Game game, int playerId, Status status, int? score)
    {
        if (score is < 0 or > 100)
        {
            throw new ArgumentOutOfRangeException(nameof(score));
        }

        return new Entry(game, playerId, status, score);
    }
}