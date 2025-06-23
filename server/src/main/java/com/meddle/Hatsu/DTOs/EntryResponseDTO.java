package com.meddle.Hatsu.DTOs;

import com.meddle.Hatsu.Models.Game;

public record EntryResponseDTO(short status, Integer score, Game game) {

}
