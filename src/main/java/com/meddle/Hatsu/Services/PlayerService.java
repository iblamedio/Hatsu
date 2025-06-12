package com.meddle.Hatsu.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Auth.AuthResponse;
import com.meddle.Hatsu.Auth.AuthUtils;
import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Repositories.PlayerRepository;
import com.sun.jdi.request.DuplicateRequestException;

@Service
public class PlayerService {

   @Autowired
   private PlayerRepository repo;

   @Autowired
   private PasswordEncoder encoder;

   @Autowired
   private AuthUtils authUtils;

   public Player find(Long id) throws EntityNotFoundException {
      return repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Player of id " + id + " does not exist."));
   }

   public AuthResponse register(String username, String password) {
      if (repo.findByUsername(username).isPresent()) {
         throw new DuplicateRequestException("Player of username " + username + " already exists");
      }

      String encryptedPassword = encoder.encode(password);
      String token = authUtils.generateToken(username);

      repo.save(new Player(username, encryptedPassword));
      return new AuthResponse("Bearer " + token);
   }

   public AuthResponse login(String username, String encryptedPassword) throws EntityNotFoundException {
      Optional<Player> player = repo.findByUsername(username);

      System.out.println(player.get().getPassword());
      System.out.println(encryptedPassword);

      // if (player.isPresent() && )
      return null;
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
