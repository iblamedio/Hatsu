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
import com.meddle.Hatsu.Models.User;
import com.meddle.Hatsu.Services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
class UserController {

   @Autowired
   private UserService service;

   @GetMapping
   public List<User> get() {
      return service.findAll();
   }

   @GetMapping("{id}")
   public ResponseEntity<User> getOne(@PathVariable Long id) throws EntityNotFoundException {
      User user = service.find(id);
      return new ResponseEntity<User>(user, HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<User> post(@Valid @RequestBody User user) {
      User newUser = service.create(user);

      return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
   }

   @PutMapping("{id}")
   public ResponseEntity<User> put(@PathVariable Long id, @Valid @RequestBody User user)
         throws EntityNotFoundException {
      User newUser = service.update(id, user);
      return new ResponseEntity<User>(newUser, HttpStatus.OK);
   }

   @DeleteMapping("{id}")
   public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException {
      service.destroy(id);
      return new ResponseEntity<String>("Deleted user of id " + id, HttpStatus.NO_CONTENT);
   }

}
