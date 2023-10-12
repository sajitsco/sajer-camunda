package com.sajits.sajer.core.auth;

public interface AuthInterface {
    public String getToken(String userName);
    public String getUserNameFromJwtToken(String token);
    public boolean validateJwtToken(String token);
    public Visitor visit(String id, boolean authenticated, String uuid);
    public Visitor populateVisitor(String id);
    public String redirect(String code,String state, String type);
    public String addTempLoginInfo(String userId);
    public String login(String state, String type);
    public void addAuthProvider(String providerName, AuthProviderInterface authProvider);
    public String tokenFromTempKey(String tempk);
    public Object chat(String id);
    public Object sendComment(String id, String comment);
    public String loginWithToken(User user, String token);
    public User updateProfile(String id, User user);
    public UserServiceInterface getUserService();
}
