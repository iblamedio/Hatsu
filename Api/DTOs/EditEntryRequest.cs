using Domain.Enums;

namespace Api.DTOs;

public abstract record EditEntryRequest(Guid EntryId, int? Score, Status? Status);