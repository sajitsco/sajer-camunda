package com.sajits.sajer.samo.infrastructure.interfaces;

import com.sajits.sajer.samo.infrastructure.exception.TaxApiException;

public interface Signatory {

    String sign(String data) throws TaxApiException;

    String getKeyId();
}
