using Domain.Enums;

namespace Application.UseCases.EntryUseCases.CreateEntryUseCase;

public abstract record CreateEntryUseCaseInput(Guid PlayerId, long GameId, int? Score, Status? Status)
{
}