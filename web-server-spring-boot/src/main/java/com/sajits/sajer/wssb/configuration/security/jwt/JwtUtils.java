package com.sajits.sajer.wssb.configuration.security.jwt;

import com.sajits.sajer.wssb.application.UseSajer;
import com.sajits.sajer.wssb.configuration.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
	@Autowired
	UseSajer useSajer;

	public String generateJwtToken(Authentication authentication) {
		Object obj = authentication.getPrincipal();
		System.out.println(obj);
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		String token = useSajer.getAuth().getToken(userPrincipal.getUsername());

		return token;
	}


	public String getUserNameFromJwtToken(String token) {
		return useSajer.getAuth().getUserNameFromJwtToken(token);
	}
	public boolean validateJwtToken(String token) {
		return useSajer.getAuth().validateJwtToken(token);
	}
}