package com.mybank.gateway.Services;

import com.mybank.gateway.Exceptions.LoginAlreadyExistsException;
import com.mybank.gateway.Models.User;
import com.mybank.gateway.Repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.security.auth.login.LoginException;
import java.util.Collections;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        if (!userRepository.existsByLogin(login)) {
            throw new UsernameNotFoundException(login);
        }
        User user = userRepository.findByLogin(login);

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }

    public void addUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new LoginAlreadyExistsException(user.getLogin());
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}
