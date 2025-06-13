package com.meddle.Hatsu.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meddle.Hatsu.Models.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
   public List<Entry> findByPlayerId(Long playerId);

   public Optional<Entry> findByPlayerIdAndIgdbId(Long playerId, Long igdbId);

   public List<Entry> findByPlayerIdAndStatus(Long playerId, short status);
}
