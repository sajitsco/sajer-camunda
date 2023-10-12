package com.sajits.sajer.acesa.archive.application;

import com.sajits.sajer.acesa.archive.domain.Document;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component("createRegisterDocument")
public class CreateRegisterDocument implements TaskListener {

    public void execute(DelegateExecution delegate) {
        
    }

    @Override
    public void notify(DelegateTask arg0) {
        Document doc = new Document();
        arg0.setVariable("document", org.camunda.spin.Spin.JSON(doc).toString());
    }
}