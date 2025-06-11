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

import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Services.EntryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("entry")
public class EntryController {

   @Autowired
   private EntryService service;

   @GetMapping
   public ResponseEntity<List<Entry>> get() {
      return new ResponseEntity<List<Entry>>(service.findAll(), HttpStatus.OK);
   }

   @GetMapping("{userId}")
   public ResponseEntity<List<Entry>> getByUser(@PathVariable Long userId) {
      return new ResponseEntity<List<Entry>>(service.findByUser(userId), HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<Entry> post(@Valid @RequestBody Entry entry) {
      return new ResponseEntity<Entry>(service.create(entry), HttpStatus.OK);
   }

   @PutMapping("{id}")
   public ResponseEntity<Entry> put(@PathVariable Long id, @Valid @RequestBody Entry entry) {
      try {
         return new ResponseEntity<Entry>(service.update(id, entry), HttpStatus.OK);
      } catch (RuntimeException e) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   @DeleteMapping("{id}")
   public ResponseEntity<String> delete(@PathVariable Long id) {
      try {
         service.destroy(id);
         return new ResponseEntity<String>("Deleted entry of id " + id, HttpStatus.NO_CONTENT);
      } catch (RuntimeException e) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

}
