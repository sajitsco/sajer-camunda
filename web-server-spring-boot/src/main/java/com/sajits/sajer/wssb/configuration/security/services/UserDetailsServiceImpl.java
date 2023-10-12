package com.sajits.sajer.wssb.configuration.security.services;

import com.sajits.sajer.core.auth.Visitor;
import com.sajits.sajer.wssb.application.UseSajer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UseSajer useSajer;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Visitor visitor = useSajer.getVisitor(username);
		if( visitor == null ){
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		return UserDetailsImpl.build(visitor);
	}
}
