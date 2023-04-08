package com.hexaware.hexacliq.config;

import com.hexaware.hexacliq.dto.Authority;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.service.UserService;
import com.hexaware.hexacliq.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(Constants.AUTHORIZATION);
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(Constants.BEARER)) {
            try {
                jwtToken = requestTokenHeader.substring(7);
                username = this.jwtUtil.extractUsername(jwtToken);
                log.info(Constants.USER_SUCCESSFULLY_VALIDATED);
            } catch (ExpiredJwtException e) {
                log.error(Constants.JWT_TOKEN_EXPIRED);
                throw e;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw e;
            }
        } else {
            log.error(Constants.INVALID_TOKEN);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userService.loadUserByUsername(username);
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                String requestPath = request.getRequestURI();
                authorization((User) userDetails, requestPath);
            }
        } else {
            log.error(Constants.TOKEN_NOT_VALID);
        }
        filterChain.doFilter(request, response);
    }

    private static void authorization(User user, String requestPath) {
        Optional<Authority> grantedAuthority = user.getAuthorities().stream()
                .filter(ga -> requestPath != null && requestPath.contains(ga.getAuthority()))
                .findFirst();
        if(grantedAuthority.isEmpty()) {
            log.error("The user is not authorized to access a path - {}", requestPath);
            throw new AuthorizationServiceException("The user is not authorized to access a path "+ requestPath);
        } else {
            log.info("Permission granted for a path - {}", requestPath);
        }
    }
}
