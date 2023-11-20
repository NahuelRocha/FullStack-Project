package com.rocha.fullstack.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.rocha.fullstack.utils.Role.ADMIN;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/","/output.css","/transition.css",
                                        "/login","/checkout.css","/checkout.js","/shop",
                                        "/register","/product","/navbar","/footer","/login-user/**",
                                        "/api/main-product/**","/api/product/all","/form/**"
                                        ,"/api/product/all-products","/api/product/{id}"
                                        ,"/api/product/all","/api/product/products-by-name"
                                        ,"/api/product/by/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/"))
                .logout(out -> out
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .permitAll())
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1)
                                .expiredUrl("/login?expired"))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }

}
