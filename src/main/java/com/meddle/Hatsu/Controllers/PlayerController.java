package com.meddle.Hatsu.Controllers;

import java.util.List;

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

import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Services.PlayerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/player")
class PlayerController {

   @Autowired
   private PlayerService service;

   @GetMapping
   public List<Player> get() {
      return service.findAll();
   }

   @GetMapping("/{id}")
   public ResponseEntity<Player> getOne(@PathVariable Long id) throws EntityNotFoundException {
      Player player = service.find(id);
      return ResponseEntity.ok(player);
   }

   @PostMapping
   public ResponseEntity<Player> post(@Valid @RequestBody Player player) {
      Player newPlayer = service.create(player);

      return new ResponseEntity<Player>(newPlayer, HttpStatus.CREATED);
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
