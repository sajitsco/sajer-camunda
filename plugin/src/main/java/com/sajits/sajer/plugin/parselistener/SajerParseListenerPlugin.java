package com.sajits.sajer.plugin.parselistener;

import java.util.ArrayList;
import java.util.List;

import com.sajits.sajer.plugin.tasklistener.ProcessApplicationEventParseListener;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cmmn.transformer.CmmnTransformListener;

public class SajerParseListenerPlugin  extends AbstractProcessEnginePlugin {

    //protected BlockingCommandInterceptor blockingCommandInterceptor = new BlockingCommandInterceptor();

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    List<BpmnParseListener> preParseListeners = processEngineConfiguration.getCustomPreBPMNParseListeners();

    if(preParseListeners == null) {
      preParseListeners = new ArrayList<BpmnParseListener>();
      processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
    }
    preParseListeners.add(new SajerParseListener());
    preParseListeners.add(new ProcessApplicationEventParseListener());

    /*List<BpmnParseListener> postParseListeners = processEngineConfiguration.getCustomPostBPMNParseListeners();

    if(postParseListeners == null) {
      preParseListeners = new ArrayList<BpmnParseListener>();
      processEngineConfiguration.setCustomPostBPMNParseListeners(postParseListeners);
    }
    postParseListeners.add(new ProcessApplicationEventParseListener());*/


    List<CmmnTransformListener> preCmmnTransformListeners = processEngineConfiguration.getCustomPreCmmnTransformListeners();
    if(preCmmnTransformListeners == null) {
      preCmmnTransformListeners = new ArrayList<CmmnTransformListener>();
      processEngineConfiguration.setCustomPreCmmnTransformListeners(preCmmnTransformListeners);
    }

    preCmmnTransformListeners.add(new SajerCmmnTransformListener());

    /*List<CommandInterceptor> customPreCommandInterceptorsTxRequired = processEngineConfiguration.getCustomPreCommandInterceptorsTxRequired();
    if(customPreCommandInterceptorsTxRequired == null) {
      customPreCommandInterceptorsTxRequired = new ArrayList<CommandInterceptor>();
      processEngineConfiguration.setCustomPreCommandInterceptorsTxRequired(customPreCommandInterceptorsTxRequired);
    }
    customPreCommandInterceptorsTxRequired.add(blockingCommandInterceptor);

    List<CommandInterceptor> customPreCommandInterceptorsTxRequiresNew = processEngineConfiguration.getCustomPreCommandInterceptorsTxRequiresNew();
    if(customPreCommandInterceptorsTxRequiresNew == null) {
      customPreCommandInterceptorsTxRequiresNew = new ArrayList<CommandInterceptor>();
      processEngineConfiguration.setCustomPreCommandInterceptorsTxRequiresNew(customPreCommandInterceptorsTxRequiresNew);
    }
    customPreCommandInterceptorsTxRequiresNew.add(blockingCommandInterceptor);*/
  }

}