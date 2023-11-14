package br.edu.ciriguela.config.jwt;

import br.edu.ciriguela.config.user.User;
import br.edu.ciriguela.config.user.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Date;

import static br.edu.ciriguela.config.jwt.SecurityConstants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext applicationContext) {
        this.userService = applicationContext.getBean(UserService.class);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);
            UserDetails details = userService.loadUserByUsername(credentials.getEmail());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            details.getAuthorities())
            );
        } catch (IOException e) {
        	LOGGER.info("IOException: [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        org.springframework.security.core.userdetails.User user = ((org.springframework.security.core.userdetails.User) auth.getPrincipal());
		String jws = Jwts.builder()
			.subject(user.getUsername())
			.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.issuedAt(new Date())
			.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
			.claim("roles", user.getAuthorities()).compact();
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + jws);
    }
}

