package br.edu.ciriguela.config.security;

import static br.edu.ciriguela.config.jwt.SecurityConstants.PROFESSOR_URL;
import static br.edu.ciriguela.config.jwt.SecurityConstants.STUDENT_URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import br.edu.ciriguela.config.jwt.JwtAuthenticationFilter;
import br.edu.ciriguela.config.jwt.JwtAuthorizationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true ,jsr250Enabled = true)
public class SecurityConfig implements ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

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
	@Profile({"default", "dev"})
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(authz) -> authz
					.requestMatchers(HttpMethod.POST, "/login").permitAll()
					.requestMatchers(HttpMethod.GET, "/v3/api-docs", "/v3/api-docs/**","/v3/api-docs/swagger-config","/v3/api-docs.yaml", "/swagger-ui.html", "/swagger-ui/**", "/js/**", "/css/**", "/img/**").permitAll()
					.requestMatchers(HttpMethod.POST, PROFESSOR_URL).authenticated()
					.requestMatchers(STUDENT_URL).hasAnyRole("ADMIN", "PROFESSOR")
					.anyRequest().authenticated())
			.csrf(CsrfConfigurer::disable)
			.formLogin(FormLoginConfigurer::disable)
			.logout(LogoutConfigurer::disable)
			.addFilter(new JwtAuthenticationFilter(authenticationManager, applicationContext))
			.addFilter(new JwtAuthorizationFilter(authenticationManager))
			.addFilter(new ExceptionTranslationFilter(new Http403ForbiddenEntryPoint()))
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	@Profile("test")
	public SecurityFilterChain filterChainForTest(HttpSecurity http) throws Exception {
		LOGGER.info("filterChainForTest in profile test. ");
		http.authorizeHttpRequests(
				(authz) -> authz.anyRequest().permitAll())
			.cors(CorsConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.formLogin(FormLoginConfigurer::disable)
			.logout(LogoutConfigurer::disable)
			.addFilter(new ExceptionTranslationFilter(new Http403ForbiddenEntryPoint()))
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
