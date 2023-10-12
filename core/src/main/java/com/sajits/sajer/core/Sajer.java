package com.sajits.sajer.core;

import com.sajits.sajer.core.auth.AuthInterface;
import com.sajits.sajer.core.engine.EngineInterface;

public class Sajer implements SajerInterface {
    private AuthInterface auth;
    private EngineInterface engine;

    public Sajer(AuthInterface auth, EngineInterface engine) {
        this.auth = auth;
        this.engine = engine;
    }

    public AuthInterface getAuth() {
        return this.auth;
    }

    @Override
    public EngineInterface getEngine() {
        return this.engine;
    }
    
}
