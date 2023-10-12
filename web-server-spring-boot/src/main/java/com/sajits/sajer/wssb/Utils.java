package com.sajits.sajer.wssb;

import com.sajits.sajer.wssb.configuration.security.services.UserDetailsImpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static String getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = "";
        if( authentication.getName() != "anonymousUser" ){
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            String currentPrincipalName = userPrincipal.getVisitor().getId();
            id = currentPrincipalName;
        }
        return id;
    }
}
