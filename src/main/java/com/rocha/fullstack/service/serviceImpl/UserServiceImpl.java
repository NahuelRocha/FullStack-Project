package com.rocha.fullstack.service.serviceImpl;

import com.rocha.fullstack.config.AuthenticationRequest;
import com.rocha.fullstack.config.AuthenticationResponse;
import com.rocha.fullstack.config.jwtService.JwtService;
import com.rocha.fullstack.dto.formValidation.ValidEmail;
import com.rocha.fullstack.models.User;
import com.rocha.fullstack.repository.UserRepository;
import com.rocha.fullstack.service.UserService;
import com.rocha.fullstack.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public boolean createUser(User user) {

        Optional<User> checkEmailAlreadyTaken = userRepository.findByEmail(user.getEmail());

        if (checkEmailAlreadyTaken.isEmpty()) {

            var newUser = User.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .role(Role.USER)
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
            userRepository.save(newUser);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean findByEmail(ValidEmail email) {

        String getEmail = email.email();

        var checkEmail = userRepository.findByEmail(getEmail);

        return checkEmail.isEmpty();
    }

    @Override
    public AuthenticationResponse auth(AuthenticationRequest auth) {

        var user = userRepository.findByEmail(auth.getEmail())
                .orElseThrow();

        Map<String, Object> setRole = new HashMap<>();
        setRole.put("Role", user.getRole());

        var jwtToken = jwtService.generateToken(setRole, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
