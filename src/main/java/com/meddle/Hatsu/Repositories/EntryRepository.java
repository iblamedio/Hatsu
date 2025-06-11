package com.meddle.Hatsu.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meddle.Hatsu.Models.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
   public List<Entry> findByUserId(Long userId);
}
