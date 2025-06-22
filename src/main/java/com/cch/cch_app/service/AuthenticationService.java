package com.cch.cch_app.service;

import com.cch.cch_app.model.Name;
import com.cch.cch_app.model.User;
import com.cch.cch_app.repository.NameRepository;
import com.cch.cch_app.repository.UserRepository;
import com.cch.cch_app.dto.LoginUserDto;
import com.cch.cch_app.dto.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        // Here we need to check if the full name provided in input corresponds to a name in the name repository
        if (nameRepository.findByName(input.getFull_name()) != null) {
            System.out.println("Name found in name repo, allow linking of user");

            User user = new User(input.getFull_name(), input.getUsername(), passwordEncoder.encode(input.getPassword()));
            user.setEnabled(true);

            return userRepository.save(user);
        } else {
            System.out.println("Provided name not found in allowed names");
            return new User();
        }
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified, please verify");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return user;
    }

//    public void verifyUser(VerifyUserDto input) {
//        Optional<User> optionalUser = userRepository.findByUsername(input.getUsername());
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//
//            // This logic can pretty much be thrown out because we won't be doing verification codes.
//            if (user.getVerificationCodeExpired().isBefore(LocalDateTime.now())) {
//                throw new RuntimeException("Verification code has expired");
//            }
//
//            if (user.getVerificationCode().equals(input.getVerificationCode())) {
//                user.setEnabled(true);
//                user.setVerificationCode(null);
//                user.setVerificationCodeExpired(null);
//                userRepository.save(user);
//            } else {
//                throw new RuntimeException("Invalid Verification Code");
//            }
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }


}
