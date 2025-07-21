using Domain.Enums;

namespace Application.UseCases.EntryUseCases.CreateEntryUseCase;

public record CreateEntryUseCaseInput(Guid PlayerId, long GameId, int? Score, Status? Status)
{
}