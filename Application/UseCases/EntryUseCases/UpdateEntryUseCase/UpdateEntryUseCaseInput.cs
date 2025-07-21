using Domain.Enums;

namespace Application.UseCases.EntryUseCases.UpdateEntryUseCase;

public abstract record UpdateEntryUseCaseInput(Guid PlayerId, Guid EntryId, Status? Status, int? Score);