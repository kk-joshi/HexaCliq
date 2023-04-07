package com.hexaware.hexacliq.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class UserDto {
	private Integer id;
    private String userName;
    private long empId;
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
	private String email;
	private String phone;

}
