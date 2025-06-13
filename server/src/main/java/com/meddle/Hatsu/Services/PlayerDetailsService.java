package com.meddle.Hatsu.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meddle.Hatsu.Models.Player;
import com.meddle.Hatsu.Repositories.PlayerRepository;

@Service
public class PlayerDetailsService implements UserDetailsService {

   @Autowired
   private PlayerRepository repo;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Player player = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Player of name " + username + " does not exist."));

      return User.builder().username(player.getUsername()).password(player.getPassword()).roles("USER").build();
   }

}
