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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meddle.Hatsu.DTOs.EntryPlayerDTO;
import com.meddle.Hatsu.DTOs.EntryResponseDTO;
import com.meddle.Hatsu.Exceptions.DuplicateEntityException;
import com.meddle.Hatsu.Exceptions.EntityNotFoundException;
import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Models.NewEntryRequest;
import com.meddle.Hatsu.Models.UpdateEntryDto;
import com.meddle.Hatsu.Services.EntryService;
import com.meddle.Hatsu.Services.GameService;
import com.meddle.Hatsu.Services.PlayerService;

@RestController
@RequestMapping("/api/v1/entry")
public class EntryController {

   @Autowired
   private EntryService service;

   @Autowired
   private GameService gameService;

   @Autowired
   private PlayerService playerService;

   @GetMapping
   public ResponseEntity<List<EntryResponseDTO>> getByPlayerToken(@RequestHeader("Authorization") String authHeader)
         throws EntityNotFoundException {
      if (authHeader == null || !authHeader.startsWith("Bearer")) {
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      String token = authHeader.substring(7);

      List<Entry> entries = service.findByToken(token);

      EntryPlayerDTO player = new EntryPlayerDTO(playerService.getByToken(token).getUsername());

      List<EntryResponseDTO> entryDtos = entries.stream()
            .map(entry -> new EntryResponseDTO(player, entry.getStatus(), entry.getScore(),
                  gameService.getById(entry.getIgdbId())))
            .toList();

      return new ResponseEntity<List<EntryResponseDTO>>(entryDtos, HttpStatus.OK);
   }

   @GetMapping("/{playerId}")
   public ResponseEntity<List<EntryResponseDTO>> getByPlayer(@PathVariable Long playerId)
         throws EntityNotFoundException {
      List<Entry> entries = service.findByPlayer(playerId);

      EntryPlayerDTO player = new EntryPlayerDTO(playerService.getById(playerId).getUsername());

      List<EntryResponseDTO> entryDtos = entries.stream()
            .map(entry -> new EntryResponseDTO(player, entry.getStatus(), entry.getScore(),
                  gameService.getById(entry.getIgdbId())))
            .toList();

      return new ResponseEntity<List<EntryResponseDTO>>(entryDtos, HttpStatus.OK);
   }

   @GetMapping("/{playerId}/{igdbId}")
   public ResponseEntity<EntryResponseDTO> getByPlayerAndIgdb(@PathVariable Long playerId, @PathVariable Long igdbId)
         throws EntityNotFoundException {
      Entry entry = service.findByPlayerAndIgdb(playerId, igdbId);
      EntryPlayerDTO player = new EntryPlayerDTO(playerService.getById(playerId).getUsername());

      EntryResponseDTO entryDto = new EntryResponseDTO(player, entry.getStatus(),
            entry.getScore(),
            gameService.getById(igdbId));

      return new ResponseEntity<EntryResponseDTO>(entryDto, HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<Entry> post(@RequestBody NewEntryRequest entryDto,
         @RequestHeader("Authorization") String authHeader)
         throws DuplicateEntityException {
      if (authHeader == null || !authHeader.startsWith("Bearer")) {
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      String token = authHeader.substring(7);
      return new ResponseEntity<Entry>(service.create(entryDto, token), HttpStatus.OK);
   }

   @PutMapping
   public ResponseEntity<Entry> put(@RequestBody UpdateEntryDto updateEntryDto,
         @RequestHeader("Authorization") String authHeader) {
      if (authHeader == null || !authHeader.startsWith("Bearer")) {
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      String token = authHeader.substring(7);
      return new ResponseEntity<Entry>(service.update(token, updateEntryDto), HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      service.destroy(id);
      return ResponseEntity.noContent().build();
   }

}
