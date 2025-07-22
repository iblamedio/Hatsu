using Domain.Enums;

namespace Api.DTOs;

public record EditEntryRequest(Guid EntryId, int? Score, Status? Status);