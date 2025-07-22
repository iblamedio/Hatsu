using Domain.Enums;

namespace Api.DTOs;

public record CreateEntryRequest(long GameId, int? Score, Status? Status);