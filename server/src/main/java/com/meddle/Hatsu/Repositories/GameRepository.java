package com.meddle.Hatsu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meddle.Hatsu.Models.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
