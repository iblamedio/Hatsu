package com.meddle.Hatsu.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meddle.Hatsu.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
