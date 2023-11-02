package com.rocha.fullstack.service;

import com.rocha.fullstack.dto.formValidation.ValidEmail;
import com.rocha.fullstack.models.User;

public interface UserService {

    boolean createUser(User user);

    boolean findByEmail(ValidEmail email);
}
