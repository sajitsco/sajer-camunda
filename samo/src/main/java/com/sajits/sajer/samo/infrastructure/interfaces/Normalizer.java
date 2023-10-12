package com.sajits.sajer.samo.infrastructure.interfaces;

import java.util.Map;

public interface Normalizer {

    String normalize(Object data, Map<String, String> headers);
}
