package com.sajits.sajer.plugin.tasklistener;

import java.util.List;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.Resource;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;


public class SajerTaskListener implements TaskListener {
  private static SajerTaskListener instance = null;

  protected SajerTaskListener() { }

  public static SajerTaskListener getInstance() {
    if(instance == null) {
      instance = new SajerTaskListener();
    }
    return instance;
  }

  public void notify(DelegateTask delegateTask) {
    ProcessDefinition pd = BpmPlatform.getDefaultProcessEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(delegateTask.getProcessDefinitionId()).singleResult();
    Deployment d = BpmPlatform.getDefaultProcessEngine().getRepositoryService().createDeploymentQuery().deploymentId(pd.getDeploymentId()).singleResult();
    List<Resource> rls = BpmPlatform.getDefaultProcessEngine().getRepositoryService().getDeploymentResources(d.getId());
    for (Resource resource : rls) {
      //if( resource.getName().equals(delegateTask.getTaskDefinitionKey()+".sds")){
      if( resource.getName().contains(delegateTask.getTaskDefinitionKey()) && resource.getName().contains(".sds")) {
        String sds = new String(resource.getBytes());
        ObjectValue obj1 = Variables.serializedObjectValue(sds).serializationDataFormat("application/json").objectTypeName("com.sajits.sajer.core.entities.DataStructure").create();
        delegateTask.setVariableLocal("DataStructure", obj1);
      }
    }
  }

}