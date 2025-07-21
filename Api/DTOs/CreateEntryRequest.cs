using Domain.Enums;

namespace Api.DTOs;

public abstract record CreateEntryRequest(long GameId, int? Score, Status? Status);