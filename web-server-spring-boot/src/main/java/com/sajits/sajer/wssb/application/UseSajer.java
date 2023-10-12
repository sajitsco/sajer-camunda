package com.sajits.sajer.wssb.application;

import com.sajits.sajer.core.auth.AuthInterface;
import com.sajits.sajer.core.auth.Visitor;
import com.sajits.sajer.core.engine.EngineInterface;
import com.sajits.sajer.camunda.auth.UserService;
import com.sajits.sajer.camunda.engine.CamundaEngine;
import com.sajits.sajer.core.Sajer;
import com.sajits.sajer.core.SajerInterface;
import com.sajits.sajer.library.auth.Authentication;
import com.sajits.sajer.library.auth.providers.Google;

import org.springframework.stereotype.Component;

@Component
public class UseSajer {
    private SajerInterface sajer;

    public UseSajer() {
        sajer = new Sajer(new Authentication(new UserService()), new CamundaEngine());
        // sajer.getAuth().addAuthProvider("google", new Google("test.sajits.com"));
        sajer.getAuth().addAuthProvider("google", new Google());
        sajer.getEngine().initializeEngine();
    }

    public Visitor visit(String id, boolean authenticated, String uuid) {
        return sajer.getAuth().visit(id, authenticated, uuid);
    }

    public AuthInterface getAuth() {
        return sajer.getAuth();
    }

    public EngineInterface getEngine() {
        return sajer.getEngine();
    }

    public Visitor getVisitor(String id) {
        return sajer.getAuth().populateVisitor(id);
    }

}
