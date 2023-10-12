package com.sajits.sajer.plugin.executionlistener;

import org.camunda.bpm.engine.delegate.CaseExecutionListener;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;

public class SajerCaseExecutionListener implements CaseExecutionListener {

    @Override
    public void notify(DelegateCaseExecution arg0) throws Exception {
        System.out.println("*SCL*" + "  Activity Name : " + arg0.getActivityName() + "  Event Name : " + arg0.getEventName()) ;
    }
    
}
