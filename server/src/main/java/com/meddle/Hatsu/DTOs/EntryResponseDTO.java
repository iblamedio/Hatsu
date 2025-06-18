package com.meddle.Hatsu.DTOs;

import com.meddle.Hatsu.Models.Game;

public record EntryResponseDTO(EntryPlayerDTO player, short status, Integer score, Game game) {

}
