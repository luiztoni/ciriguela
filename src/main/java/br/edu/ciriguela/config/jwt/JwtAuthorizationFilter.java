package br.edu.ciriguela.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static br.edu.ciriguela.config.jwt.SecurityConstants.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	AuthenticationManager authManager;
	
	public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {

          	Claims claims = Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.build()
				.parseSignedClaims(token.replace(TOKEN_PREFIX, "")).getPayload();
			String user =  claims.getSubject();

            @SuppressWarnings("unchecked")
            var list = (List<Map<String, String>>) claims.get("roles");


            List<GrantedAuthority> authorities;
            authorities = list.stream().map(item ->
                    new SimpleGrantedAuthority(item.get("authority"))
            ).collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }

            return null;
        }
        return null;
    }
}

