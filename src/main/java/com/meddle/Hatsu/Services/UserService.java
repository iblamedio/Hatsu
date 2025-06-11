package com.meddle.Hatsu.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Models.User;
import com.meddle.Hatsu.Repositories.UserRepository;

@Service
public class UserService {

   @Autowired
   private UserRepository repo;

   public User find(Long id) {
      return repo.findById(id).orElseThrow(() -> new RuntimeException());
   }

   public List<User> findAll() {
      return repo.findAll();
   }

   public User create(User user) {
      return repo.save(user);
   }

   public User update(Long id, User user) throws RuntimeException {
      User oldUser = repo.findById(id).orElseThrow(() -> new RuntimeException());

      oldUser.setUsername(user.getUsername());
      oldUser.setPassword(user.getPassword());

      return repo.save(oldUser);
   }

   public void destroy(Long id) throws RuntimeException {
      if (repo.existsById(id)) {
         repo.deleteById(id);
         return;
      }
      throw new RuntimeException();
   }

}
