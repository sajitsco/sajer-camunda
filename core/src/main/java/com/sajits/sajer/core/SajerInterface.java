package com.sajits.sajer.core;

import com.sajits.sajer.core.auth.AuthInterface;
import com.sajits.sajer.core.engine.EngineInterface;

public interface SajerInterface {
    AuthInterface getAuth();
    EngineInterface getEngine();
}
