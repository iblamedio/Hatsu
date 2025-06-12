package com.meddle.Hatsu.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Exceptions.DuplicateEntityException;
import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Repositories.EntryRepository;
import com.meddle.Hatsu.Repositories.PlayerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EntryService {

   @Autowired
   private EntryRepository repo;

   @Autowired
   private PlayerRepository playerRepository;

   public Entry find(Long id) {
      return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Entry of id " + id + " already exists"));
   }

   public List<Entry> findByPlayer(Long playerId) {
      if (!playerRepository.existsById(playerId)) {
         throw new EntityNotFoundException("Player of id " + playerId + " does not exist.");
      }
      return repo.findByPlayerId(playerId);
   }

   public Entry findByPlayerAndIgdb(Long playerId, Long igdbId) {
      if (!playerRepository.existsById(playerId)) {
         throw new EntityNotFoundException("Player of id " + playerId + " does not exist.");
      }

      return repo.findByPlayerIdAndIgdbId(playerId, igdbId)
            .orElseThrow(() -> new EntityNotFoundException("Specified entry does not exist"));
   }

   public Entry create(Entry entry) throws DuplicateEntityException {
      if (repo.findByPlayerIdAndIgdbId(entry.getUserId(), entry.getIgdbId()).isPresent()) {
         throw new DuplicateEntityException("This entry already exists");
      }

      return repo.save(entry);
   }

   public Entry update(Long id, Entry entry) {
      Entry oldEntry = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Entry of id " + id + " does not exist."));

      oldEntry.setScore(entry.getStatus());
      oldEntry.setScore(entry.getScore());

      return repo.save(oldEntry);
   }

   public void destroy(Long id) {
      if (!repo.existsById(id)) {
         throw new EntityNotFoundException("Entry of id " + id + " does not exist.");
      }

      repo.deleteById(id);
   }

}
