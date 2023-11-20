package com.rocha.fullstack.controller;

import com.rocha.fullstack.config.ApplicationConfig;
import com.rocha.fullstack.config.jwtService.JwtService;
import com.rocha.fullstack.models.User;
import com.rocha.fullstack.service.serviceImpl.UserServiceImpl;
import com.rocha.fullstack.dto.formValidation.ValidEmail;
import com.rocha.fullstack.dto.formValidation.ValidLastname;
import com.rocha.fullstack.dto.formValidation.ValidName;
import com.rocha.fullstack.dto.formValidation.ValidPassword;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid User user, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("Fix incorrect fields");
        }

        boolean createUser = userService.createUser(user);
        if (createUser) {
            UserDetails userDetails = applicationConfig.userDetailsService()
                    .loadUserByUsername(user.getEmail());
            String jwtToken = jwtService.generateToken(userDetails);

            Cookie jwtCookie = new Cookie("jwt", jwtToken);
            jwtCookie.setMaxAge(-1);
            jwtCookie.setHttpOnly(true);
            response.addCookie(jwtCookie);

            return ResponseEntity.ok("SUCCESS, please login.");

        } else {
            return ResponseEntity.ok("Email already taken");
        }
    }

    @PostMapping("/check-email")
    @ResponseBody
    public ResponseEntity<String> checkEmail(@Valid ValidEmail email, BindingResult bindingResult) {

        if (email.email() == null || email.email().trim().isEmpty()) {
            return ResponseEntity.ok("");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("Invalid email format");
        } else {

            boolean emailAvailable = userService.findByEmail(email);

            if (emailAvailable) {
                return ResponseEntity.ok("");
            } else {
                return ResponseEntity.ok("Email not available");
            }
        }
    }

    @PostMapping("/check-name")
    @ResponseBody
    public ResponseEntity<String> checkName(@Valid ValidName firstname, BindingResult bindingResult) {

        if (firstname.firstname() == null || firstname.firstname().trim().isEmpty()) {
            return ResponseEntity.ok("");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("Invalid name format");
        } else {
            return ResponseEntity.ok("");
        }
    }

    @PostMapping("/check-lastname")
    @ResponseBody
    public ResponseEntity<String> checkLastName(@Valid ValidLastname lastname, BindingResult bindingResult) {

        if (lastname.lastname() == null || lastname.lastname().trim().isEmpty()) {
            return ResponseEntity.ok("");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("Invalid lastname format");
        } else {
            return ResponseEntity.ok("");
        }
    }

    @PostMapping("/check-password")
    @ResponseBody
    public ResponseEntity<String> checkPassword(@Valid ValidPassword password, BindingResult bindingResult) {

        if (password.password() == null || password.password().trim().isEmpty()) {
            return ResponseEntity.ok("");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("The password must contain at least one number, letter and special character(@$!%*?&).");
        } else {
            return ResponseEntity.ok("");
        }
    }

}
