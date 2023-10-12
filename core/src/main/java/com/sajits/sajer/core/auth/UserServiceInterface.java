package com.sajits.sajer.core.auth;

import com.sajits.sajer.core.auth.setting.Setting;

public interface UserServiceInterface {
    public boolean isThereATask(String id);

    public User userFromTask(String id);

    public User createTask(String id, User user);

    public Object chat(String id);

    public Object sendComment(String id, String comment);

    public User updateProfile(String id, User user);

    public Setting getSetting(String id);

    public Setting updateSetting(String id, Setting setting);

    public boolean isThereAUser(String id);

    public void registerUser(User user);
}
