package com.hexaware.hexacliq.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String authority;

    public Authority(String roleName) {
        this.authority = roleName;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
