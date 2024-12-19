package com.dipndeep.crudproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dipndeep.crudproject.models.User;
import com.dipndeep.crudproject.repository.UserRepository;

@Controller
public class AuthController {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @GetMapping("/login")
   public String login() {
      return "auth/login";
   }

   @GetMapping("/register")
   public String showRegisterForm(Model model) {
      model.addAttribute("user", new User());
      return "auth/register";
   }

   @PostMapping("/register")
   public String registerUser(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return "redirect:/login?success";
   }
}
