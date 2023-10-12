package com.sajits.sajer.plugin.tasklistener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;


public class SajerTaskListener2 implements TaskListener {
  private static SajerTaskListener instance = null;

  public SajerTaskListener2() { }

  public static SajerTaskListener getInstance() {
    if(instance == null) {
      instance = new SajerTaskListener();
    }
    return instance;
  }

  public void notify(DelegateTask delegateTask) {
    System.out.println("*STL*" + "  " + delegateTask.getTaskDefinitionKey() + "  " + delegateTask.getEventName());
  }

}