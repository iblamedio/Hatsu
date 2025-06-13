package com.meddle.Hatsu.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Auth.AuthUtils;
import com.meddle.Hatsu.Exceptions.DuplicateEntityException;
import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Models.NewEntryRequest;
import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Models.UpdateEntryDto;
import com.meddle.Hatsu.Repositories.EntryRepository;
import com.meddle.Hatsu.Repositories.PlayerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EntryService {

   @Autowired
   private EntryRepository repo;

   @Autowired
   private PlayerRepository playerRepository;

   @Autowired
   private AuthUtils authUtils;

   public Entry find(Long id) {
      return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Entry of id " + id + " already exists"));
   }

   public List<Entry> findByPlayer(Long playerId) {
      if (!playerRepository.existsById(playerId)) {
         throw new EntityNotFoundException("Player of id " + playerId + " does not exist.");
      }
      return repo.findByPlayerId(playerId);
   }

   public List<Entry> findByToken(String token) {
      String username = authUtils.extractUsername(token);
      Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Player " + username + " not found"));

      return repo.findByPlayerId(player.getId());
   }

   public Entry findByPlayerAndIgdb(Long playerId, Long igdbId) {
      if (!playerRepository.existsById(playerId)) {
         throw new EntityNotFoundException("Player of id " + playerId + " does not exist.");
      }

      return repo.findByPlayerIdAndIgdbId(playerId, igdbId)
            .orElseThrow(() -> new EntityNotFoundException("Specified entry does not exist"));
   }

   public Entry create(NewEntryRequest newEntryDto, String token) throws DuplicateEntityException {
      String username = authUtils.extractUsername(token);
      Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Player " + username + " not found"));

      if (repo.findByPlayerIdAndIgdbId(player.getId(), newEntryDto.igdbId()).isPresent()) {
         throw new DuplicateEntityException("This entry already exists");
      }

      Entry newEntry = null;

      if (newEntryDto.score() == null) {
         newEntry = new Entry(player.getId(), newEntryDto.igdbId(), newEntryDto.status());
         return repo.save(newEntry);
      }

      newEntry = new Entry(player.getId(), newEntryDto.igdbId(), newEntryDto.score(), newEntryDto.status());
      return repo.save(newEntry);
   }

   public Entry update(String token, UpdateEntryDto updateEntryDto) {
      String username = authUtils.extractUsername(token);
      Player player = playerRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Player " + username + " not found"));

      Entry oldEntry = repo.findByPlayerIdAndIgdbId(player.getId(), updateEntryDto.igdbId())
            .orElseThrow(() -> new EntityNotFoundException("Entry of player id " + player.getId() + " and game id "
                  + updateEntryDto.igdbId() + " does not exist."));

      oldEntry.setStatus(updateEntryDto.status());

      if (updateEntryDto.score() != null) {
         oldEntry.setScore(updateEntryDto.score());
      }

      return repo.save(oldEntry);
   }

   public void destroy(Long id) {
      if (!repo.existsById(id)) {
         throw new EntityNotFoundException("Entry of id " + id + " does not exist.");
      }

      repo.deleteById(id);
   }

}
