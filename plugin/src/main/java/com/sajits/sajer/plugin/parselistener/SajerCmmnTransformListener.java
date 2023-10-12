package com.sajits.sajer.plugin.parselistener;

import java.util.List;

import com.sajits.sajer.plugin.executionlistener.SajerCaseExecutionListener;
import com.sajits.sajer.plugin.tasklistener.SajerTaskListener2;

import org.camunda.bpm.engine.impl.cmmn.model.CmmnActivity;
import org.camunda.bpm.engine.impl.cmmn.model.CmmnCaseDefinition;
import org.camunda.bpm.engine.impl.cmmn.model.CmmnSentryDeclaration;
import org.camunda.bpm.engine.impl.cmmn.transformer.CmmnTransformListener;
import org.camunda.bpm.model.cmmn.impl.instance.CasePlanModel;
import org.camunda.bpm.model.cmmn.instance.Case;
import org.camunda.bpm.model.cmmn.instance.CaseTask;
import org.camunda.bpm.model.cmmn.instance.DecisionTask;
import org.camunda.bpm.model.cmmn.instance.Definitions;
import org.camunda.bpm.model.cmmn.instance.EventListener;
import org.camunda.bpm.model.cmmn.instance.HumanTask;
import org.camunda.bpm.model.cmmn.instance.Milestone;
import org.camunda.bpm.model.cmmn.instance.PlanItem;
import org.camunda.bpm.model.cmmn.instance.ProcessTask;
import org.camunda.bpm.model.cmmn.instance.Sentry;
import org.camunda.bpm.model.cmmn.instance.Stage;
import org.camunda.bpm.model.cmmn.instance.Task;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.camunda.bpm.engine.delegate.CaseExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;

public class SajerCmmnTransformListener implements CmmnTransformListener {

    public final static CaseExecutionListener CASE_EXECUTION_LISTENER = new SajerCaseExecutionListener();
    public final static TaskListener TASK_LISTENER = new SajerTaskListener2();
    
    @Override
    public void transformCase(Case arg0, CmmnCaseDefinition arg1) {
        System.out.println("__transformCase__");
        addListeners(arg1);
    }

    @Override
    public void transformCasePlanModel(CasePlanModel arg0, CmmnActivity arg1) {
        System.out.println("__transformCasePlanModel__");
        addListeners(arg1);
    }

    @Override
    public void transformCasePlanModel(org.camunda.bpm.model.cmmn.instance.CasePlanModel arg0, CmmnActivity arg1) {
        System.out.println("__transformCasePlanModel2__");
        addListeners(arg1);
    }

    @Override
    public void transformCaseTask(PlanItem arg0, CaseTask arg1, CmmnActivity arg2) {
        System.out.println("__transformCaseTask__");
        addListeners(arg2);
        
    }

    @Override
    public void transformDecisionTask(PlanItem arg0, DecisionTask arg1, CmmnActivity arg2) {
        System.out.println("__transformDecisionTask__");
        addListeners(arg2);
        
    }

    @Override
    public void transformEventListener(PlanItem arg0, EventListener arg1, CmmnActivity arg2) {
        System.out.println("__transformEventListener__");
        addListeners(arg2);
        
    }

    @Override
    public void transformHumanTask(PlanItem arg0, HumanTask arg1, CmmnActivity arg2) {
        System.out.println("__transformHumanTask__");
        addListeners(arg2);
        //arg2.addListener("assignment", TASK_LISTENER);
        //arg2. 
    }

    @Override
    public void transformMilestone(PlanItem arg0, Milestone arg1, CmmnActivity arg2) {
        System.out.println("__transformMilestone__");
        addListeners(arg2);
        
    }

    @Override
    public void transformProcessTask(PlanItem arg0, ProcessTask arg1, CmmnActivity arg2) {
        System.out.println("__transformProcessTask__");
        addListeners(arg2);
        
    }

    @Override
    public void transformRootElement(Definitions arg0, List<? extends CmmnCaseDefinition> arg1) {
        System.out.println("__transformRootElement__");
        for (CmmnCaseDefinition cmmnCaseDefinition : arg1) {
            List<CmmnActivity> acs = cmmnCaseDefinition.getActivities();
            for (CmmnActivity activity : acs) {
                activityTreeListeners(activity);
            }
        }
        
    }

    @Override
    public void transformSentry(Sentry arg0, CmmnSentryDeclaration arg1) {
        System.out.println("__transformSentry__");
    }

    @Override
    public void transformStage(PlanItem arg0, Stage arg1, CmmnActivity arg2) {
        System.out.println("__transformStage__");
        addListeners(arg2);
    }

    @Override
    public void transformTask(PlanItem arg0, Task arg1, CmmnActivity arg2) {
        System.out.println("__transformTask__");
        addListeners(arg2);
    }

    private void addListeners(CmmnActivity activity) {
        /*activity.addListener(CaseExecutionListener.CLOSE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.COMPLETE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.CREATE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.DISABLE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.ENABLE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.EXIT, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.MANUAL_START, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.OCCUR, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.PARENT_RESUME, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.PARENT_SUSPEND, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.PARENT_TERMINATE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.RESUME, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.RE_ACTIVATE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.RE_ENABLE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.START, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.SUSPEND, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.TERMINATE, CASE_EXECUTION_LISTENER);*/
        
        //activity.addListener(CaseExecutionListener.CREATE, CASE_EXECUTION_LISTENER);
    }

    private void activityTreeListeners(CmmnActivity activity) {
        System.out.println("__activity__" + activity.getName());
        activity.addListener(CaseExecutionListener.CLOSE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.COMPLETE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.CREATE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.DISABLE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.ENABLE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.EXIT, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.MANUAL_START, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.OCCUR, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.PARENT_RESUME, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.PARENT_SUSPEND, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.PARENT_TERMINATE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.RESUME, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.RE_ACTIVATE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.RE_ENABLE, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.START, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.SUSPEND, CASE_EXECUTION_LISTENER);
        activity.addListener(CaseExecutionListener.TERMINATE, CASE_EXECUTION_LISTENER);
        
        for (CmmnActivity act : activity.getActivities()) {
            activityTreeListeners(act);
            for (DomElement  act2 : act.getCmmnElement().getDomElement().getChildElements()) {
                System.out.println(act2.getLocalName());
            }
        }
    }
    
}
