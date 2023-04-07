package com.hexaware.hexacliq.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_role")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userRoleId;
	private String roleName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne
	private Role role;
}
