package com.sajits.sajer.wssb.configuration.security.services;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sajits.sajer.core.auth.Visitor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Visitor visitor;
	

	public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	public UserDetailsImpl(Visitor visitor) {
		
		this.visitor = visitor;
	}
	public static UserDetailsImpl build(Visitor user) {
		return new UserDetailsImpl(user);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority[] ats = {new SimpleGrantedAuthority("ROLE_USER")};
		List<GrantedAuthority> authorities = Arrays.asList(ats);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return "";
	}
	@Override
	public String getUsername() {
		return this.visitor.getUser().getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

	/*@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}*/
}
