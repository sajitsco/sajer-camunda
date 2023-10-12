package com.sajits.sajer.wssb.configuration.security;
import com.sajits.sajer.wssb.configuration.security.jwt.AuthEntryPointJwt;
import com.sajits.sajer.wssb.configuration.security.jwt.AuthTokenFilter;
import com.sajits.sajer.wssb.configuration.security.jwt.MJEncoder;
import com.sajits.sajer.wssb.configuration.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver
		= new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(52428800);
		return multipartResolver;
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new MJEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll()
            .antMatchers("/*").permitAll()
            .antMatchers("/s/**").permitAll()
            .antMatchers("/apps/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/fonts/**").permitAll()
            .antMatchers("/icons/**").permitAll()
            .antMatchers("/js/**").permitAll()
			.antMatchers("/p/auth/visit").permitAll()
			.antMatchers("/p/auth/redirect").permitAll()
			.antMatchers("/p/auth/login").permitAll()
			.antMatchers("/p/auth/token-login").permitAll()
			//.antMatchers("/p/tpmanagement/**").permitAll()
			.anyRequest().authenticated();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.headers().frameOptions().disable();
	}
}
