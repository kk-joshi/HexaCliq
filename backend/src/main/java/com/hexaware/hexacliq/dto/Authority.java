package com.hexaware.hexacliq.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;

@Data
public class Authority implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
