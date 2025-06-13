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

import com.meddle.Hatsu.Exceptions.DuplicateEntityException;
import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Models.NewEntryRequest;
import com.meddle.Hatsu.Models.UpdateEntryDto;
import com.meddle.Hatsu.Services.EntryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/entry")
public class EntryController {

   @Autowired
   private EntryService service;

   @GetMapping
   public ResponseEntity<List<Entry>> getByPlayerToken(@RequestHeader("Authorization") String authHeader) {
      if (authHeader == null || !authHeader.startsWith("Bearer")) {
         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      String token = authHeader.substring(7);
      return new ResponseEntity<List<Entry>>(service.findByToken(token), HttpStatus.OK);
   }

   @GetMapping("/{playerId}")
   public ResponseEntity<List<Entry>> getByPlayer(@PathVariable Long playerId) {
      return new ResponseEntity<List<Entry>>(service.findByPlayer(playerId), HttpStatus.OK);
   }

   @GetMapping("/{playerId}/{igdbId}")
   public ResponseEntity<Entry> getByPlayerAndIgdb(@PathVariable Long playerId, @PathVariable Long igdbId) {
      return new ResponseEntity<Entry>(service.findByPlayerAndIgdb(playerId, igdbId), HttpStatus.OK);
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
