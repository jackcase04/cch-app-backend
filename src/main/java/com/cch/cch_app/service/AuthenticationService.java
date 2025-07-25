package com.cch.cch_app.service;

import com.cch.cch_app.exception.NameAlreadyRegisteredException;
import com.cch.cch_app.exception.NameNotAllowedException;
import com.cch.cch_app.exception.InvalidLoginException;
import com.cch.cch_app.model.Name;
import com.cch.cch_app.model.User;
import com.cch.cch_app.repository.NameRepository;
import com.cch.cch_app.repository.UserRepository;
import com.cch.cch_app.dto.LoginUserDto;
import com.cch.cch_app.dto.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final NameRepository nameRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            NameRepository nameRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.nameRepository = nameRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // Signup functionality signs up the user and logs them in for simplicity
    public User signup(RegisterUserDto input) {

        if (nameRepository.findByName(input.getFull_name()) == null) {
            System.out.println("Name not found in allowed names list");
            throw new NameNotAllowedException("Name not found in allowed names list");
        }

        if (userRepository.findByFullname(input.getFull_name()) != null) {
            System.out.println("Name already registered by another user");
            throw new NameAlreadyRegisteredException("Name already registered by another user");
        }

        User user = new User(input.getFull_name(), input.getUsername(), passwordEncoder.encode(input.getPassword()), input.getExpopushtoken());
        user.setEnabled(true);

        userRepository.save(user);
        LoginUserDto newUser = new LoginUserDto(input.getUsername(), input.getPassword(), input.getExpopushtoken());

        Name name = nameRepository.findByName(input.getFull_name());
        name.setAccountassociated(true);
        nameRepository.save(name);

        return authenticate(newUser);
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password init"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );

            user.setEnabled(true);
            user.setExpopushtoken(input.getExpopushtoken());
            userRepository.save(user);

        } catch (AuthenticationException e) {
            throw new InvalidLoginException("Invalid username or password");
        }

        return user;
    }

}
