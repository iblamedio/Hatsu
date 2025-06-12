package com.meddle.Hatsu.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Auth.AuthResponse;
import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Repositories.PlayerRepository;
import com.sun.jdi.request.DuplicateRequestException;

@Service
public class PlayerService {

   @Autowired
   private PlayerRepository repo;

   public Player find(Long id) throws EntityNotFoundException {
      return repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Player of id " + id + " does not exist."));
   }

   public AuthResponse register(String username, String password) {
      if (repo.findByUsername(username).isPresent()) {
         throw new DuplicateRequestException("Player of username " + username + " already exists");
      }

      // TODO: encrypt password

      repo.save(new Player(username, password));
      // TODO: return token
      return new AuthResponse("token for " + username);
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
