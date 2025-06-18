package com.meddle.Hatsu.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Models.Game;
import com.meddle.Hatsu.Repositories.GameRepository;

@Service
public class GameService {

   @Autowired
   private GameRepository repo;

   public Game getById(Long gameId) {
      if (repo.existsById(gameId)) {
         return repo.findById(gameId).get();
      }

      // fetch from igdb
      return new Game();

   }

}
