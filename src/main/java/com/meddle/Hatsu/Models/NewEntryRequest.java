package com.meddle.Hatsu.Models;

public record NewEntryRequest(Long playerId, Long igdbId, Integer score, short status) {
}
