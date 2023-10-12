package com.sajits.sajer.plugin.executionlistener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class SajerExecutionListener implements ExecutionListener {

  // notify method is executed when Execution Listener is called
  public void notify(DelegateExecution execution) throws Exception {
    System.out.println("*SEL*" + "  BKey : " + execution.getBusinessKey() + "  PBKey : " + execution.getProcessBusinessKey() + "  Activity Name : " + execution.getCurrentActivityName() + "  " + execution.getEventName());
  }
}
