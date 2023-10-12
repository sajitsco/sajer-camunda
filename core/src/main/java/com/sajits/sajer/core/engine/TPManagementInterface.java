package com.sajits.sajer.core.engine;

import java.util.List;
import java.util.Map;

public interface TPManagementInterface {
    Map<String, Object> tasks(String id);
    STask updateTask(String id, STask task);
    String completeTask(STask task, String userId);
    String delegateTask(String taskId, String userId);
    String resolveTask(String taskId);
    String deleteTask(String taskId, String reason);
    String setCandidateForTask(String taskId, String candidate);
    Map<String, List<Map<String, String>>> setTPMOptions(String userId);
    String addTP(String id, String type, String name, String userId);
    String getDS(String id, String userId);
}
