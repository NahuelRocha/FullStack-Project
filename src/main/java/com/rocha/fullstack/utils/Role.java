package com.rocha.fullstack.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public enum Role {

    USER(Set.of(Permission.USER_ACCESS)),
    ADMIN(Set.of(Permission.ADMIN_ACCESS))

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){

        var authorities = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;

    };

}
