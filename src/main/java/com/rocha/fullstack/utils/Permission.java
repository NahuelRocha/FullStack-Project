package com.rocha.fullstack.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_ACCESS("user_access"),
    ADMIN_ACCESS("admin_access"),

    ;


    @Getter
    private final String permission;

}
