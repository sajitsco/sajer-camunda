package com.sajits.sajer.core.auth;

import java.util.Date;

public class Visitor {
  private String id;
  private String uuid;
  private String token;
  private Date lastVisit;
  private Date exp;
  public String getId() {
    return id;
}
public void setId(String id) {
    this.id = id;
}
public String getUuid() {
    return uuid;
}
public void setUuid(String uuid) {
    this.uuid = uuid;
}
public String getToken() {
    return token;
}
public void setToken(String token) {
    this.token = token;
}
public Date getLastVisit() {
    return lastVisit;
}
public void setLastVisit(Date lastVisit) {
    this.lastVisit = lastVisit;
}
public Date getExp() {
    return exp;
}
public void setExp(Date exp) {
    this.exp = exp;
}
public User getUser() {
    return user;
}
public void setUser(User user) {
    this.user = user;
}
private User user;
}
