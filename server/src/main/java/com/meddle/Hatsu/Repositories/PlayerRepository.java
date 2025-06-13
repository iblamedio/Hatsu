package com.meddle.Hatsu.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meddle.Hatsu.Models.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
   public Optional<Player> findByUsername(String username);
}
