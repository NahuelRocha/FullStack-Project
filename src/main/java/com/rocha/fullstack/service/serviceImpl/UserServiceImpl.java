package com.rocha.fullstack.service.serviceImpl;

import com.rocha.fullstack.dto.formValidation.ValidEmail;
import com.rocha.fullstack.models.User;
import com.rocha.fullstack.repository.UserRepository;
import com.rocha.fullstack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public boolean createUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }

        var newUser = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        userRepository.save(newUser);

        return true;
    }

    @Override
    public boolean findByEmail(ValidEmail email) {

        String getEmail = email.email();

        var checkEmail = userRepository.findByEmail(getEmail);

        return checkEmail.isEmpty();
    }
}
