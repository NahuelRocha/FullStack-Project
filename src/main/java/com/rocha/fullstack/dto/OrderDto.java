package com.rocha.fullstack.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public record OrderDto(
         String firstname,
         String lastname,
         String address,
         String zipcode,
         String city,
         String email,
         String phone
) {

}
