package com.sajits.sajer.camunda.auth;

import com.sajits.sajer.core.auth.User;
import com.sajits.sajer.core.auth.UserServiceInterface;
import com.sajits.sajer.core.auth.setting.Setting;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;


public class UserService implements UserServiceInterface {

    private TaskService taskService = BpmPlatform.getDefaultProcessEngine().getTaskService();

    public boolean isThereATask(String id) {
        long count = taskService.createTaskQuery().taskName(id + " visited").count();
        return count > 0;
    }

    public boolean isThereAUser(String id) {
        long count = BpmPlatform.getDefaultProcessEngine().getIdentityService().createUserQuery().userId(id).count();
        return count > 0;
    }

    @Override
    public User userFromTask(String id) {
        Task task = taskService.createTaskQuery().taskName(id + " visited").singleResult();
        User user = null;
        if (task != null) {
            user = (User) taskService.getVariable(task.getId(), "User");
        }
        return user;
    }

    @Override
    public User createTask(String id, User user) {
        Task task = taskService.newTask();
        task.setName(id + " visited");
        task.setTenantId("sajer");
        taskService.saveTask(task);
        taskService.setVariable(task.getId(), "User", user);
        return user;
    }

    public void registerUser(User user) {
        if (BpmPlatform.getDefaultProcessEngine().getIdentityService().createUserQuery()
                .userId(user.getEmail()).count() == 0) {
            org.camunda.bpm.engine.identity.User newUser = BpmPlatform.getDefaultProcessEngine().getIdentityService()
                    .newUser(user.getEmail());
            newUser.setPassword(user.getEmail());
            newUser.setFirstName(user.getFname());
            newUser.setLastName(user.getLname());
            newUser.setEmail(user.getEmail());
            BpmPlatform.getDefaultProcessEngine().getIdentityService().saveUser(newUser);
            BpmPlatform.getDefaultProcessEngine().getIdentityService().createMembership(user.getEmail(),"public");
        }
    }

    @Override
    public Object chat(String id) {
        Task task = taskService.createTaskQuery().taskName(id + " visited").singleResult();
        return taskService.getTaskComments(task.getId());
    }

    public Object sendComment(String id, String comment) {
        Task task = taskService.createTaskQuery().taskName(id + " visited").singleResult();
        BpmPlatform.getDefaultProcessEngine().getIdentityService().setAuthentication(id, null, null);
        taskService.createComment(task.getId(), null, comment);
        BpmPlatform.getDefaultProcessEngine().getIdentityService().clearAuthentication();
        return taskService.getTaskComments(task.getId());
    }

    public User updateProfile(String id, User user) {
        Task task = taskService.createTaskQuery().taskName(id + " visited").singleResult();
        taskService.setVariable(task.getId(), "User", user);
        User us = (User) taskService.getVariable(task.getId(), "User");
        return us;
    }

    public Setting updateSetting(String id, Setting setting) {
        Task task = taskService.createTaskQuery().taskName(id + " visited").singleResult();
        ObjectValue typedCustomerValue = Variables.objectValue(setting).serializationDataFormat("application/json").create();
        taskService.setVariableLocal(task.getId(), "Setting", typedCustomerValue);
        Setting st = (Setting) taskService.getVariableLocal(task.getId(), "Setting");
        if( st.getSections() == null ){
            return setting;
        }
        return st;
    }

    public Setting getSetting(String id) {
        Task task = taskService.createTaskQuery().taskName(id + " visited").singleResult();
        Setting st = (Setting) taskService.getVariable(task.getId(), "Setting");
        if (st == null) {
            st = new Setting();
            taskService.setVariable(task.getId(), "Setting", st);
        }
        return st;
    }

}
