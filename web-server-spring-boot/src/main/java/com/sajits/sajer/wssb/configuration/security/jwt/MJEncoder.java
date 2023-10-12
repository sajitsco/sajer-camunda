package com.sajits.sajer.wssb.configuration.security.jwt;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MJEncoder implements PasswordEncoder {
    public java.lang.String encode(java.lang.CharSequence rawPassword){
        return "";
    }
  
  public boolean matches(java.lang.CharSequence rawPassword, java.lang.String encodedPassword){
      return true;
  }
  
  public boolean upgradeEncoding(java.lang.String encodedPassword) {
    return false;
  }
}
