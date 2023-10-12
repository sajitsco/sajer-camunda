package com.sajits.sajer.acesa.archive.application;

import com.sajits.sajer.acesa.archive.domain.Document;
import com.sajits.sajer.acesa.archive.domain.File;
import com.sajits.sajer.acesa.archive.infrastructure.DocumentRepository;
import com.sajits.sajer.acesa.archive.infrastructure.FileRepository;
import com.sajits.sajer.acesa.archive.infrastructure.GoogleDrive;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.camunda.spin.Spin.JSON;

import java.util.Base64;

@Component("registerDocument")
public class RegisterDocument   implements JavaDelegate {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FileRepository fileRepository;

      
    public void execute(DelegateExecution delegate) {
      
      Object dc = delegate.getVariable("document");
      if(dc!= null ){
        System.out.println(dc);
        Document doc = JSON(dc).mapTo(Document.class);
        if( doc.getFiles().size() > 0) {
          for (File file : doc.getFiles()) {
            file.setPath(new String(Base64.getDecoder().decode(file.getPath().getBytes())));
            file.setPath(GoogleDrive.saveFile(file));
            //File f = new File();
            //f.setName(file.getName());
            //f.setPath(file.getPath());
            fileRepository.save(file);
          }
        }
        documentRepository.save(doc);
      }

      System.out.println(GoogleDrive.listFiles());

    }
}
