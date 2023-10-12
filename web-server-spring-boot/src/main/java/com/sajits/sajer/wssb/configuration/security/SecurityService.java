package com.sajits.sajer.wssb.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
  
   public boolean hasAccess(int parameter) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    System.out.println(currentPrincipalName);
       return parameter == 1234;
   }
}