package com.prabhu.customermanagementportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerResponseDto {
    String first_name;
    String last_name;
    String street;
    String address;
    String city;
    String state;

    String email;

    String phone;
}
