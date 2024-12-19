package com.dipndeep.crudproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dipndeep.crudproject.models.User;
import com.dipndeep.crudproject.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

      @Autowired
      private UserRepository userRepository;

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword()) // Password harus terenkripsi
                        .roles(user.getRole()) // Default role
                        .build();
      }
}
