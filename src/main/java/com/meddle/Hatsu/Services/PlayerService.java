package com.meddle.Hatsu.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Auth.AuthResponse;
import com.meddle.Hatsu.Auth.AuthUtils;
import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Exceptions.InvalidCredentialsException;
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

   public AuthResponse register(String username, String password) throws InvalidCredentialsException {
      if (repo.findByUsername(username).isPresent()) {
         throw new DuplicateRequestException("Player of username " + username + " already exists");
      }

      if (username.length() < 3) {
         throw new InvalidCredentialsException("Username must be at least 4 characters long");
      }

      if (username.contains(" ")) {
         throw new InvalidCredentialsException("Can't have blank spaces in username");
      }

      if (password.length() <= 6) {
         throw new InvalidCredentialsException("Password must be at least 4 characters long");
      }

      String encryptedPassword = encoder.encode(password);
      String token = authUtils.generateToken(username);

      repo.save(new Player(username, encryptedPassword));
      return new AuthResponse(token);
   }

   public AuthResponse login(String username, String password) throws InvalidCredentialsException {
      Optional<Player> player = repo.findByUsername(username);

      if (player.isPresent() && encoder.matches(password, player.get().getPassword())) {
         String token = authUtils.generateToken(username);
         return new AuthResponse(token);
      }

      throw new InvalidCredentialsException("Credenciais inválidas");

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
