package com.dipndeep.crudproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dipndeep.crudproject.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsername(String username);
}
