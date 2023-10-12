package com.sajits.sajer.plugin.parselistener;

import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import org.camunda.bpm.engine.delegate.TaskListener;
import com.sajits.sajer.plugin.tasklistener.SajerTaskListener;
import com.sajits.sajer.plugin.tasklistener.SajerTaskListener3;

public class SajerParseListener extends AbstractBpmnParseListener {

  @Override
  public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
    ActivityBehavior activityBehavior = activity.getActivityBehavior();
    if(activityBehavior instanceof UserTaskActivityBehavior ){
      UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
        userTaskActivityBehavior
        .getTaskDefinition()
        .addTaskListener(TaskListener.EVENTNAME_CREATE, SajerTaskListener.getInstance());

        userTaskActivityBehavior
        .getTaskDefinition()
        .addTaskListener(TaskListener.EVENTNAME_ASSIGNMENT, SajerTaskListener3.getInstance());

    }
  }
}
