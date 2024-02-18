package com.prabhu.customermanagementportal.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Customer {
    @Id
    String uuid;

    String first_name;
    String last_name;
    String street;
    String address;
    String city;
    String state;
    @Column(unique = true,nullable = false)
    String email;

    String phone;
}
