package com.sajits.sajer.camunda.engine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sajits.sajer.camunda.utilities.Util;
import com.sajits.sajer.core.engine.STask;
import com.sajits.sajer.core.engine.TPManagementInterface;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.camunda.spin.json.SpinJsonNode;

public class TPManagement implements TPManagementInterface{

    @Override
    public Map<String, Object> tasks(String id) {
        Map<String, Object> taskMap = new HashMap<String, Object>();
        Util.setAuthentication(id);
        List<Task> tasks = BpmPlatform.getDefaultProcessEngine().getTaskService().createTaskQuery().initializeFormKeys().list();
        
        BpmPlatform.getDefaultProcessEngine().getIdentityService().clearAuthentication();
        for (Task task : tasks) {
            CamundaTask camundaTask = new CamundaTask();
            camundaTask.populateTask(task, id);
            taskMap.put(task.getId(), camundaTask);
        }
        /**/List<HistoricTaskInstance> tsks=BpmPlatform.getDefaultProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().taskOwner(id).list();
        for (HistoricTaskInstance task : tsks) {
            if( task.getEndTime() != null){
                CamundaTask camundaTask = new CamundaTask();
            camundaTask.populateTaskFromHistoricTask(task, id);
            taskMap.put(task.getId(), camundaTask);
            }
        }
        return taskMap;
    }
    
    @Override
    public STask updateTask(String id, STask task) {
        CamundaTask camundaTask = new CamundaTask();
        camundaTask.loadFromSTask(task, id);
        return camundaTask;
    }

    @Override
    public String completeTask(STask task, String userId) {
        CamundaTask camundaTask = new CamundaTask();
        camundaTask.loadFromSTask(task, userId);
        camundaTask.completeTask(task);
        return "Task Compeleted";
    }

    @Override
    public String delegateTask(String taskId, String userId) {
        BpmPlatform.getDefaultProcessEngine().getTaskService().delegateTask(taskId, userId);
        return "Delegated";
    }

    @Override
    public String resolveTask(String taskId) {
        BpmPlatform.getDefaultProcessEngine().getTaskService().resolveTask(taskId);
        return "Resolved";
    }

    @Override
    public String deleteTask(String taskId, String reason) {
        BpmPlatform.getDefaultProcessEngine().getTaskService().deleteTask(taskId, reason);
        return "Deleted";
    }

    @Override
    public String setCandidateForTask(String taskId, String candidate) {
        BpmPlatform.getDefaultProcessEngine().getTaskService().addCandidateUser(taskId, candidate);
        return "Set Candidate";
    }

    @Override
    public Map<String, List<Map<String, String>>> setTPMOptions(String userId) {
        Map<String, List<Map<String, String>>> opMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> taskOptions = new ArrayList<Map<String, String>>();
        Map<String, String> op;


        try {
            try (InputStream in = getClass().getResourceAsStream("/dataStructures/b.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    SpinJsonNode json = org.camunda.spin.Spin.JSON(reader);
                    for (int i = 0; i < json.elements().size(); i++) {
                        SpinJsonNode  ds = json.elements().get(i);
                        op = new HashMap<String, String>();
                        op.put("label", ds.prop("label").stringValue());
                        op.put("value", ds.prop("id").stringValue());
                        op.put("description", ds.prop("description").stringValue());
                        op.put("icon", ds.prop("icon").stringValue());
                        taskOptions.add(op);
                    }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        opMap.put("task", taskOptions);

        List<Map<String, String>> processOptions = new ArrayList<Map<String, String>>();

        Util.setAuthentication(userId);
        List<ProcessDefinition> pds = BpmPlatform.getDefaultProcessEngine().getRepositoryService().createProcessDefinitionQuery().list();
        BpmPlatform.getDefaultProcessEngine().getIdentityService().clearAuthentication();

        for (ProcessDefinition processDefinition : pds) {
            op = new HashMap<String, String>();
            op.put("label", processDefinition.getName());
            op.put("value", processDefinition.getId());
            String description = "";
            if( processDefinition.getDescription() != null ){
                description = " : " + processDefinition.getDescription();
            }
            op.put("description", "version : " + Integer.toString(processDefinition.getVersion()) +  description);
            op.put("icon", "account_tree");
            processOptions.add(op);
        }

        opMap.put("process", processOptions);
        return opMap;
    }

    @Override
    public String addTP(String id, String type, String name, String userId) {
        if( type.equals("task")) {

        }
        if( type.equals("process") ){
            BpmPlatform.getDefaultProcessEngine().getRuntimeService().startProcessInstanceById(id, name);
        }
        return "TP Added";
    }

    @Override
    public String getDS(String id, String userId) {
        try {
            try (InputStream in = getClass().getResourceAsStream("/dataStructures/b.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    SpinJsonNode json = org.camunda.spin.Spin.JSON(reader);
                    for (int i = 0; i < json.elements().size(); i++) {
                        SpinJsonNode  ds = json.elements().get(i);
                        if( ds.prop("id").stringValue().equals(id) )
                        {
                            SpinJsonNode dataStructure = ds.prop("dataStructure");
                            return dataStructure.toString();
                        }
                    }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
    
}
