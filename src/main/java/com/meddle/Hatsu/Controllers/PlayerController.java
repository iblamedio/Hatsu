package com.meddle.Hatsu.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meddle.Hatsu.Auth.AuthRequest;
import com.meddle.Hatsu.Auth.AuthResponse;
import com.meddle.Hatsu.Exceptions.DuplicateEntityException;
import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Exceptions.InvalidCredentialsException;
import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Services.PlayerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/player")
class PlayerController {

   @Autowired
   private PlayerService service;

   @GetMapping("/{id}")
   public ResponseEntity<Player> getOne(@PathVariable Long id) throws EntityNotFoundException {
      Player player = service.find(id);
      return ResponseEntity.ok(player);
   }

   @PostMapping("/register")
   public ResponseEntity<AuthResponse> post(@RequestBody AuthRequest request) throws DuplicateEntityException {
      AuthResponse token = service.register(request.username(), request.password());

      return new ResponseEntity<AuthResponse>(token, HttpStatus.CREATED);
   }

   @PostMapping("/login")
   public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request)
         throws InvalidCredentialsException {
      return ResponseEntity.ok(service.login(request.username(), request.password()));
   }

   @PutMapping("/{id}")
   public ResponseEntity<Player> put(@PathVariable Long id, @Valid @RequestBody Player player)
         throws EntityNotFoundException {
      Player newPlayer = service.update(id, player);
      return ResponseEntity.ok(newPlayer);
   }

   @DeleteMapping("{id}")
   public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException {
      service.destroy(id);
      return new ResponseEntity<String>("Deleted player of id " + id, HttpStatus.NO_CONTENT);
   }

}
