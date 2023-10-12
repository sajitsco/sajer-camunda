package com.sajits.sajer.core.auth;

import java.io.IOException;

public interface AuthProviderInterface{
    String login(String state);
    User redirect(String code, String state) throws IOException;
    int validateToken(String token) throws IOException;
}