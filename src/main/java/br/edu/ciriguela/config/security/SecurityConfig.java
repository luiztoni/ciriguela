package br.edu.ciriguela.config.security;

import static br.edu.ciriguela.config.jwt.SecurityConstants.PROFESSOR_URL;
import static br.edu.ciriguela.config.jwt.SecurityConstants.STUDENT_URL;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import br.edu.ciriguela.config.jwt.JwtAuthenticationFilter;
import br.edu.ciriguela.config.jwt.JwtAuthorizationFilter;

@Configuration
public class SecurityConfig implements ApplicationContextAware {

	@Value("${ciriguela.disable.security}")
	private boolean disableSecurity;

	private ApplicationContext applicationContext;
	
	private AuthenticationManager authenticationManager;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public SecurityConfig(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		if (disableSecurity) {
			http.authorizeHttpRequests(
					(authz) -> authz.anyRequest().permitAll())
					.csrf().disable()
					.cors().disable()
					.httpBasic().disable()
					.formLogin().disable()
					.logout().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and().exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

			return http.build();
		}
		http.authorizeHttpRequests(
				(authz) -> authz
						.requestMatchers(HttpMethod.GET, "/v3/api-docs", "/swagger-ui/*").permitAll()
						.requestMatchers(HttpMethod.POST, PROFESSOR_URL).authenticated()
						.requestMatchers(STUDENT_URL).hasAnyRole("ADMIN","PROFESSOR")
						.anyRequest().authenticated())
				.csrf().disable()
				.formLogin().disable()
				.logout().disable()
				.addFilter(new JwtAuthenticationFilter(authenticationManager, applicationContext))
				.addFilter(new JwtAuthorizationFilter(authenticationManager))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		
		return http.build();
	}
}
