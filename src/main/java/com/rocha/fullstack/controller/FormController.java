package com.rocha.fullstack.controller;

import com.rocha.fullstack.models.User;
import com.rocha.fullstack.service.serviceImpl.UserServiceImpl;
import com.rocha.fullstack.dto.formValidation.ValidEmail;
import com.rocha.fullstack.dto.formValidation.ValidLastname;
import com.rocha.fullstack.dto.formValidation.ValidName;
import com.rocha.fullstack.dto.formValidation.ValidPassword;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormController {

    private final UserServiceImpl userService;

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            return ResponseEntity.ok("Fix incorrect fields");
        }

        boolean createUser = userService.createUser(user);

        if (createUser){
            return ResponseEntity.ok("SUCCESS");
        } else {
            return ResponseEntity.ok("Fix incorrect fields");
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
            return ResponseEntity.ok("Use at least one number and one capital letter.");
        } else {
            return ResponseEntity.ok("");
        }
    }

}
