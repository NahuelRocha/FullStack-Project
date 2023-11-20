package com.rocha.fullstack.controller;

import com.rocha.fullstack.config.ApplicationConfig;
import com.rocha.fullstack.config.AuthenticationRequest;
import com.rocha.fullstack.config.jwtService.JwtService;
import com.rocha.fullstack.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login-user")
@Slf4j
public class LoginController {


    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@Valid AuthenticationRequest auth, BindingResult errors, HttpServletResponse response) {

        if (errors.hasErrors()) {

            return ResponseEntity.ok("Invalid username/password");
        }

        try {
            UserDetails userDetails = applicationConfig.userDetailsService()
                    .loadUserByUsername(auth.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getEmail(),
                            auth.getPassword()
                    )
            );
            String jwtToken = jwtService.generateToken(userDetails);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("jwt{}", jwtToken);
            Cookie jwtCookie = new Cookie("jwt", jwtToken);
            jwtCookie.setMaxAge(-1);
            jwtCookie.setHttpOnly(true);
            response.addCookie(jwtCookie);

            if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority
                    .getAuthority().equals("ROLE_ADMIN"))){

                return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "admin").body("admin");
            } else {

                return ResponseEntity.status(HttpStatus.FOUND).header("HX-Redirect", "user").body("user");
            }


        } catch (Exception e) {
            return ResponseEntity.ok("Invalid username/password");
        }
    }

}
