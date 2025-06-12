package com.meddle.Hatsu.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Repositories.PlayerRepository;

@Service
public class PlayerService {

   @Autowired
   private PlayerRepository repo;

   public Player find(Long id) throws EntityNotFoundException {
      return repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Player of id " + id + " does not exist."));
   }

   public List<Player> findAll() {
      return repo.findAll();
   }

   public Player create(Player player) {
      return repo.save(player);
   }

   public Player update(Long id, Player player) throws EntityNotFoundException {
      Player oldPlayer = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Player of id " + id + " does not exist."));

      oldPlayer.setUsername(player.getUsername());
      oldPlayer.setPassword(player.getPassword());

      return repo.save(oldPlayer);
   }

   public void destroy(Long id) throws EntityNotFoundException {
      if (!repo.existsById(id)) {
         throw new EntityNotFoundException("Player of id " + id + " does not exist.");
      }
      repo.deleteById(id);
   }

}
