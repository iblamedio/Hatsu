using Domain.Enums;

namespace Application.UseCases.EntryUseCases.CreateEntryUseCase;

public abstract class CreateEntryUseCaseInput(Guid playerId, long gameId, int? score)
{
    public Guid PlayerId { get; init; } = playerId;
    public long GameId { get; init; } = gameId;
    public int? Score { get; init; } = score;
    public Status Status { get; init; } = Status.Planning;
}