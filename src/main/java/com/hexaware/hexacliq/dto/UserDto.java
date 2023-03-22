package com.hexaware.hexacliq.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userName;
    private long empId;
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
}
