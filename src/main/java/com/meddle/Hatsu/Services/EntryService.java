package com.meddle.Hatsu.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Models.Entry;
import com.meddle.Hatsu.Repositories.EntryRepository;

@Service
public class EntryService {

   @Autowired
   private EntryRepository repo;

   public List<Entry> findAll() {
      return repo.findAll();
   }

   public List<Entry> findByUser(Long userId) {
      return repo.findByUserId(userId);
   }

   public Entry create(Entry entry) {
      return repo.save(entry);
   }

   public Entry update(Long id, Entry entry) throws RuntimeException {
      Entry oldEntry = repo.findById(id).orElseThrow(() -> new RuntimeException());

      oldEntry.setScore(entry.getStatus());
      oldEntry.setScore(entry.getScore());

      return repo.save(oldEntry);
   }

   public void destroy(Long id) throws RuntimeException {
      if (repo.existsById(id)) {
         repo.deleteById(id);
         return;
      }

      throw new RuntimeException();
   }

}
