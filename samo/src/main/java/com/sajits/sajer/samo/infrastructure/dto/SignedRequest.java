package com.sajits.sajer.samo.infrastructure.dto;

public class SignedRequest {

    private String signature;

    private String signatureKeyId;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureKeyId() {
        return signatureKeyId;
    }

    public void setSignatureKeyId(String signatureKeyId) {
        this.signatureKeyId = signatureKeyId;
    }
}
