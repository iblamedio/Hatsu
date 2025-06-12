package com.meddle.Hatsu.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Repositories.EntryRepository;
import com.meddle.Hatsu.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EntryService {

   @Autowired
   private EntryRepository repo;

   @Autowired
   private UserRepository userRepo;

   public List<Entry> findAll() {
      return repo.findAll();
   }

   public List<Entry> findByUser(Long userId) {
      if (!userRepo.existsById(userId)) {
         throw new EntityNotFoundException("User of id " + userId + " does not exist.");
      }
      return repo.findByUserId(userId);
   }

   public Entry create(Entry entry) {
      return repo.save(entry);
   }

   public Entry update(Long id, Entry entry) {
      Entry oldEntry = repo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Entry of id " + id + " does not exist."));

      oldEntry.setScore(entry.getStatus());
      oldEntry.setScore(entry.getScore());

      return repo.save(oldEntry);
   }

   public void destroy(Long id) {
      if (!repo.existsById(id)) {
         throw new EntityNotFoundException("Entry of id " + id + " does not exist.");
      }

      repo.deleteById(id);
   }

}
