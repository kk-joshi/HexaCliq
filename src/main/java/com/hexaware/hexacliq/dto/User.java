package com.hexaware.hexacliq.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_master")
public class User {
    @Id
    @GeneratedValue
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "emp_id")
    private long empId;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
}
