package com.sajits.sajer.camunda.engine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sajits.sajer.core.engine.STask;
import com.sajits.sajer.core.engine.TaskLifecycleOperations;
import com.sajits.sajer.core.engine.TaskLifecycleStates;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.json.SpinJsonNode;

import com.sajits.sajer.core.entities.DataStructure;
import com.sajits.sajer.core.entities.Field;
import com.sajits.sajer.core.entities.FieldOption;
import com.sajits.sajer.core.entities.FieldType;
import com.sajits.sajer.core.entities.Section;

import java.util.UUID;

public class CamundaTask extends STask {

    public void populateTaskFromHistoricTask(HistoricTaskInstance task, String userId) {
        this.setId(task.getId());
        this.setName(task.getName());

        DataStructure ts;// = (DataStructure) BpmPlatform.getDefaultProcessEngine().getTaskService().getVariable(task.getId(), "DataStructure");
        Map<String, Section> sections;
        //if( ts == null ){
            ts = new DataStructure();
            sections = new HashMap<String, Section>();
            ts.setSections(sections);
        //}
        sections = ts.getSections();
        ts.setCustomizable(false);
        Section section1 = new Section();
        section1.setCustomizable(false);
        section1.setEditable(true);
        section1.setVisible(true);
        section1.setIcon("home");
        section1.setIndex(0);
        section1.setLabel("General");
        Map<String, Field> fields = new HashMap<String, Field>();
        List<FieldOption> os = new ArrayList<FieldOption>();
        fields.put("giSection", createField(fields.size(), "General Info", FieldType.section_break, "", new ArrayList<FieldOption>(), false, false, true, true));
        fields.put("taskId", createField(fields.size(), "Task Id", FieldType.text, task.getId(), os, true, false, false, true));
        fields.put("taskName", createField(fields.size(), "Task Name", FieldType.text, task.getName(), os, true, false, false, true));
        fields.put("startTime", createField(fields.size(), "Start Time", FieldType.text, task.getStartTime(), os, true, false, false, true));
        fields.put("aiSection", createField(fields.size(), "Authorization Info", FieldType.section_break, "", new ArrayList<FieldOption>(), false, false, true, true));
        fields.put("taskAssignee", createField(fields.size(), "Assignee", FieldType.text, task.getAssignee(), os, true, false, false, true));
        fields.put("taskOwner", createField(fields.size(), "Owner", FieldType.text, task.getOwner(), os, true, false, false, true));
        fields.put("fSection", createField(fields.size(), "Final Status Info", FieldType.section_break, "", new ArrayList<FieldOption>(), false, false, true, true));
        fields.put("deleteReason", createField(fields.size(), "Delete Reason", FieldType.text, task.getDeleteReason(), os, true, false, false, true));
        fields.put("endTime", createField(fields.size(), "End Time", FieldType.text, task.getEndTime(), os, true, false, false, true));
        fields.put("duration", createField(fields.size(), "Duration", FieldType.text, Math.round(task.getDurationInMillis()/60000), os, true, false, false, true));
        fields.put("vSection", createField(fields.size(), "Variables", FieldType.section_break, "", new ArrayList<FieldOption>(), false, false, true, true));
        /*try {
            fields.put("duration", createField("duration", "Duration", FieldType.text, Math.round(task.getDurationInMillis()/60000), os, true, false, false, true));
        } catch (Exception e) {
            System.out.println(task.getDurationInMillis());
        }*/
        List<HistoricVariableInstance> vars = BpmPlatform.getDefaultProcessEngine().getHistoryService().createHistoricVariableInstanceQuery().taskIdIn(task.getId()).list();
        for (HistoricVariableInstance historicVariableInstance : vars) {
            try {
                    SpinJsonNode val = (SpinJsonNode)historicVariableInstance.getValue();
                fields.put(historicVariableInstance.getName(), createField(fields.size(), historicVariableInstance.getName(), FieldType.text, val.toString(), os, true, false, false, true));
            
                } catch (Exception e) {
                fields.put(historicVariableInstance.getName(), createField(fields.size(), historicVariableInstance.getName(), FieldType.text, historicVariableInstance.getValue(), os, true, false, false, true));
                }}

        if( task.getExecutionId() != null ){
            vars = BpmPlatform.getDefaultProcessEngine().getHistoryService().createHistoricVariableInstanceQuery().executionIdIn(task.getExecutionId()).list();
            for (HistoricVariableInstance historicVariableInstance : vars) {
                try {
                    SpinJsonNode val = (SpinJsonNode)historicVariableInstance.getValue();
                fields.put(historicVariableInstance.getName(), createField(fields.size(), "(e)" + historicVariableInstance.getName(), FieldType.text, val.toString(), os, true, false, false, true));
            
                } catch (Exception e) {
                fields.put(historicVariableInstance.getName(), createField(fields.size(), "(e)" + historicVariableInstance.getName(), FieldType.text, historicVariableInstance.getValue(), os, true, false, false, true));
                }
                }
        }
        
        section1.setFields(fields);
        this.setInfo(section1);

        ts.setEditable(false);
        ts.setVisible(true);
        this.setDataStructure(ts);

        this.setState(TaskLifecycleStates.deleted);
        if( task.getDeleteReason().equals("completed") ) {
            this.setState(TaskLifecycleStates.completed);
        }
        TaskLifecycleOperations[] ops = {};
        this.setOperations(ops);
    }

    public void populateTask(Task task, String userId) {
        this.setId(task.getId());
        this.setName(task.getName());

        Map<String, Object> variables = BpmPlatform.getDefaultProcessEngine().getTaskService().getVariables(task.getId());
        this.setVariables(variables);

        DataStructure ts = (DataStructure) BpmPlatform.getDefaultProcessEngine().getTaskService().getVariableLocal(task.getId(), "DataStructure");
        Map<String, Section> sections;
        if( ts == null ){
            ts = new DataStructure();
            sections = new HashMap<String, Section>();
            ts.setSections(sections);
        }
        sections = ts.getSections();



        Section section1;

        boolean isOwner = true;
        if(task.getOwner() != null ){
            isOwner = task.getOwner().equals(userId) ;
        }

        if(task.getDelegationState() != null) {
            if(task.getDelegationState().equals(DelegationState.PENDING) && !userId.equals(task.getOwner())){
                ts.setCustomizable(isOwner);
                section1 = createGeneralSection(task, isOwner);
            } else {
                ts.setCustomizable(isOwner);
                section1 = createGeneralSection(task, isOwner);
            }
        } else {
            ts.setCustomizable(isOwner);
            section1 = createGeneralSection(task, isOwner);
        }

        if( task.isSuspended() ){
            ts.setCustomizable(false);
        }

        this.setInfo(section1);

        
        //ts.setCustomizable(true);
        ts.setEditable(true);
        ts.setVisible(true);
        this.setDataStructure(ts);

        this.setState(whatIsLifecycleState(task));
        this.setOperations(whatIsLifecycleOperations(task, userId));
    }

        private Section createGeneralSection(Task task, boolean isOwner) {
            
                Section section1 = new Section();
                section1.setCustomizable(isOwner);
                section1.setEditable(true);
                section1.setVisible(true);
                section1.setIcon("home");
                section1.setIndex(0);
                section1.setLabel("General");
                Map<String, Field> fields = new HashMap<String, Field>();

                fields.put("giSection", createField(fields.size(), "General Info", FieldType.section_break, "", new ArrayList<FieldOption>(), false, false, true, true));
                fields.put("taskId", createField(fields.size(), "Task Id", FieldType.text, task.getId(), new ArrayList<FieldOption>(), true, false, false, true));
                fields.put("delegationState", createField(fields.size(), "Delegation State", FieldType.text, task.getDelegationState(), new ArrayList<FieldOption>(), true, false, false, true));
                fields.put("createTime", createField(fields.size(), "Create Time", FieldType.date, task.getCreateTime(), new ArrayList<FieldOption>(), true, false, false, true));

                List<FieldOption> ops = new ArrayList<FieldOption>();
                ops.add(createFieldOption("description","","Change the label of task", ""));
                fields.put("taskName", createField(fields.size(), "Task Name", FieldType.text, task.getName(), ops, true, false, isOwner, true));

                fields.put("aiSection", createField(fields.size(), "Authorization Info", FieldType.section_break, "", new ArrayList<FieldOption>(), false, false, true, true));
                fields.put("taskAssignee", createField(fields.size(), "Assignee", FieldType.dropdown, task.getAssignee(), fillOptionsWithUsers(), true, false, isOwner, true));
                fields.put("taskOwner", createField(fields.size(), "Owner", FieldType.dropdown, task.getOwner(), fillOptionsWithUsers(), true, false, isOwner, true));

                fields.put("dueDate", createField(fields.size(), "Due Date", FieldType.date, task.getDueDate(), new ArrayList<FieldOption>(), true, false, isOwner, true));
                fields.put("followUpDate", createField(fields.size(), "Follow Up Date", FieldType.date, task.getFollowUpDate(), new ArrayList<FieldOption>(), true, false, isOwner, true));

                section1.setFields(fields);
                return section1;
        }

        private List<FieldOption> fillOptionsWithUsers(){
            List<FieldOption> ops = new ArrayList<FieldOption>();
            List<User> users = BpmPlatform.getDefaultProcessEngine().getIdentityService().createUserQuery().list();
            for (User user : users) {
                ops.add(createFieldOption("fieldOption",user.getFirstName() + " " + user.getLastName(),user.getId(), ""));
            }
            
            ops.add(createFieldOption("description","","Select a user", ""));
            return ops;
        }

        private Field createField(int index, String label, FieldType type, Object value, List<FieldOption> ops, boolean required, boolean customizable, boolean editable, boolean visible) {
            Field field1 = new Field();
                field1.setCid(UUID.randomUUID().toString());
                field1.setCustomizable(customizable);
                field1.setEditable(editable);
                field1.setVisible(visible);
                field1.setIndex(index);
                field1.setLabel(label);
                field1.setRequired(required);
                field1.setType(type);
                field1.setOptions(ops);
                field1.setValue(value);
                return field1;
        }

        private FieldOption createFieldOption(String type, String label, String value, String icon) {
            FieldOption op1=new FieldOption();
            op1.setType(type);
            op1.setValue(value);
            op1.setIcon(icon);
            op1.setLabel(label);
            return op1;
        }

        public void loadFromSTask(STask task, String userId) {
            Task tsk;
            this.setId(task.getId());
            this.setName(task.getName());
            this.setDataStructure(task.getDataStructure());
            this.setState(TaskLifecycleStates.assigned);
            this.setInfo(task.getInfo());
            this.setVariables(task.getVariables());
            
            if( task.getId().equals("new-task") ){
                //Util.setAuthentication(id);
                tsk = BpmPlatform.getDefaultProcessEngine().getTaskService().newTask();
                tsk.setName(task.getName());
                tsk.setAssignee(userId);
                tsk.setOwner(userId);
                BpmPlatform.getDefaultProcessEngine().getTaskService().saveTask(tsk);
                this.setId(tsk.getId());
                this.setState(TaskLifecycleStates.created);
                //BpmPlatform.getDefaultProcessEngine().getIdentityService().clearAuthentication();
            }

            tsk = BpmPlatform.getDefaultProcessEngine().getTaskService().createTaskQuery().taskId(this.getId()).initializeFormKeys().singleResult();
            
            
            if(tsk != null ){
                //BpmPlatform.getDefaultProcessEngine().getTaskService().setVariable(tsk.getId(), "DataStructure", task.getDataStructure());
                applyGeneralSection(tsk);

            }
        }
    
    private Date parseDate(Object date){
        String val = (String)date;
        Date convertedDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            convertedDate = sdf.parse(val);
        } catch (Exception e) {
            return new Date();
        }
        return convertedDate;
    }
    
    private void applyGeneralSection(Task task){
        if( !this.getState().equals(TaskLifecycleStates.created) ) {
            task.setName((String)this.getInfo().getFields().get("taskName").getValue());
            task.setAssignee((String)this.getInfo().getFields().get("taskAssignee").getValue());
            task.setOwner((String)this.getInfo().getFields().get("taskOwner").getValue());
            task.setDueDate(parseDate(this.getInfo().getFields().get("dueDate").getValue()));
            task.setFollowUpDate(parseDate(this.getInfo().getFields().get("followUpDate").getValue()));
            BpmPlatform.getDefaultProcessEngine().getTaskService().saveTask(task);            
        }
        if( this.getState().equals(TaskLifecycleStates.created)) {
            task.setName((String)this.getInfo().getFields().get("taskName").getValue());
            task.setDueDate(parseDate(this.getInfo().getFields().get("dueDate").getValue()));
            BpmPlatform.getDefaultProcessEngine().getTaskService().saveTask(task);
            this.setState(TaskLifecycleStates.assigned);            
        }

        if(this.getDataStructure().getSections().size() > 0){
            ObjectValue typedCustomerValue = Variables.objectValue(this.getDataStructure()).serializationDataFormat("application/json").create();
            BpmPlatform.getDefaultProcessEngine().getTaskService().setVariableLocal(task.getId(), "DataStructure", typedCustomerValue);
        }
    }

    private TaskLifecycleStates whatIsLifecycleState(Task task) {
        TaskLifecycleStates state = TaskLifecycleStates.unAssigned;
        if(task.getAssignee() != null){
            state = TaskLifecycleStates.assigned;
        }
        if(task.getDelegationState() != null){
            if(task.getDelegationState().equals(DelegationState.PENDING) && task.getAssignee() != null){
                state = TaskLifecycleStates.delegated;
            }
        }

        if( task.isSuspended() ){
            state = TaskLifecycleStates.suspended;
        }
        return state;
    }

    private TaskLifecycleOperations[] whatIsLifecycleOperations(Task task, String userId) {
        boolean isOwner = false;
        if(task.getOwner() != null ){
            isOwner = task.getOwner().equals(userId) ;
        }
        boolean isAssigned = false;
        if(task.getAssignee() != null){
            isAssigned = task.getAssignee().equals(userId);
        }
        boolean isDelegated = false;
        if(task.getDelegationState() != null) isDelegated = task.getDelegationState().equals(DelegationState.PENDING);

        List<TaskLifecycleOperations> operations = new ArrayList<TaskLifecycleOperations>();

        //// create : just can be set at frontend on new task creation

        //// setCandidate
        if( isOwner ) operations.add(TaskLifecycleOperations.setCandidate);

        //// save
        if(isAssigned) operations.add(TaskLifecycleOperations.save);

        //// claim
        if( task.getAssignee() == null ) operations.add(TaskLifecycleOperations.claim);

        //// unclaim
        if( isAssigned  && !isDelegated) operations.add(TaskLifecycleOperations.unclaim);

        //// assign
        if(isOwner && task.getAssignee() == null) operations.add(TaskLifecycleOperations.assign);

        //// reassign
        if(isOwner && task.getAssignee() != null) operations.add(TaskLifecycleOperations.reassign);

        //// delegate
        if( isOwner && isAssigned ) operations.add(TaskLifecycleOperations.delegate);

        //// resolve 
        if( isAssigned && isDelegated) operations.add(TaskLifecycleOperations.resolve);

        //// complete
        if(isAssigned && !isDelegated) operations.add(TaskLifecycleOperations.complete);

        //// delete
        if( isOwner ) operations.add(TaskLifecycleOperations.delete);

        TaskLifecycleOperations[] ops = new TaskLifecycleOperations[operations.size()];

        if( task.isSuspended() ){
            return new TaskLifecycleOperations[0];
        }
        return operations.toArray(ops);
    }

    public void completeTask(STask task) {
        if(this.getDataStructure().getSections().size() > 0){
            ObjectValue typedCustomerValue = Variables.objectValue(this.getDataStructure()).serializationDataFormat("application/json").create();
            BpmPlatform.getDefaultProcessEngine().getTaskService().setVariableLocal(task.getId(), "DataStructure", typedCustomerValue);
            
            for (Map.Entry<String, Section> section : this.getDataStructure().getSections().entrySet()) {
                Map<String, Object> variables = new HashMap<>();
                for (Map.Entry<String, Field> field: section.getValue().getFields().entrySet()) {
                    variables.put(field.getKey(), field.getValue().getValue());
                }

                //String str1 = org.camunda.spin.Spin.JSON(variables).toString();
                //String str2 = "\"" + str1.replaceAll("\"", "\\\\\"") + "\"";
                //ObjectValue obj1 = Variables.serializedObjectValue(str2).serializationDataFormat("application/json").objectTypeName("java.lang.Object").create();
                variables.put("taskId", this.getId());
                SpinJsonNode obj1 = org.camunda.spin.Spin.JSON(variables);
                //BpmPlatform.getDefaultProcessEngine().getTaskService().setVariable(task.getId(), section.getKey(), obj1);
                //BpmPlatform.getDefaultProcessEngine().getTaskService().setVariableLocal(task.getId(), section.getKey(), obj1);
                Task ts = BpmPlatform.getDefaultProcessEngine().getTaskService().createTaskQuery().taskId(task.getId()).singleResult();
                if( ts.getProcessInstanceId() != null ){
                    BpmPlatform.getDefaultProcessEngine().getRuntimeService().setVariableLocal(ts.getExecutionId(),  section.getKey(), obj1);
                } else {
                    BpmPlatform.getDefaultProcessEngine().getTaskService().setVariableLocal(task.getId(), section.getKey(), obj1);
                }
            }
        }
        if( this.getVariables() != null){
            for (Map.Entry<String, Object> variable : this.getVariables().entrySet()) {
            Task ts = BpmPlatform.getDefaultProcessEngine().getTaskService().createTaskQuery().taskId(task.getId()).singleResult();
            SpinJsonNode obj2 = org.camunda.spin.Spin.JSON(variable.getValue());

            if( ts.getProcessInstanceId() != null ){
                BpmPlatform.getDefaultProcessEngine().getRuntimeService().setVariableLocal(ts.getExecutionId(),  variable.getKey(), obj2);
            } else {
                BpmPlatform.getDefaultProcessEngine().getTaskService().setVariableLocal(task.getId(), variable.getKey(), obj2);
            }
        }
    }

        BpmPlatform.getDefaultProcessEngine().getTaskService().complete(task.getId());
    }
}
