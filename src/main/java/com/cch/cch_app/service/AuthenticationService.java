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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User signup(RegisterUserDto input) {
        if (nameRepository.findByName(input.getFull_name()) == null) {
            throw new NameNotAllowedException("Name not found in allowed names list");
        }

        if (userRepository.findByFullname(input.getFull_name()) != null) {
            throw new NameAlreadyRegisteredException("Name already registered by another user");
        }

        User user = new User(
                input.getFull_name(),
                input.getUsername(),
                passwordEncoder.encode(input.getPassword()),
                input.getExpopushtoken()
        );

        user.setEnabled(true);
        userRepository.save(user);

        Name name = nameRepository.findByName(input.getFull_name());
        name.setAccountassociated(true);
        nameRepository.save(name);

        // Automatically authenticate the user after registration
        return authenticate(new LoginUserDto(input.getUsername(), input.getPassword(), input.getExpopushtoken()));
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );

            // Set the authentication in the security context (creates session)
            SecurityContextHolder.getContext().setAuthentication(authentication);

            user.setEnabled(true);
            user.setExpopushtoken(input.getExpopushtoken());
            userRepository.save(user);

            return user;
        } catch (AuthenticationException e) {
            throw new InvalidLoginException("Invalid username or password");
        }
    }
}
