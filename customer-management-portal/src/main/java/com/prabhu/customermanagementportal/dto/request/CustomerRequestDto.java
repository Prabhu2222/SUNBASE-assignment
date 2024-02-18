package com.prabhu.customermanagementportal.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRequestDto {
    String first_name;
    String last_name;
    String street;
    String address;
    String city;
    String state;

    String email;

    String phone;
}
