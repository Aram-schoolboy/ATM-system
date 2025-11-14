package com.mybank.gateway.Initializers;

import com.mybank.gateway.Models.Role;
import com.mybank.gateway.Models.User;
import com.mybank.gateway.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class FirstAdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByLogin("admin")) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin"),
                        Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }
}