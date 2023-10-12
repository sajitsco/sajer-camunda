package com.sajits.sajer.samo.infrastructure.config;

import com.sajits.sajer.samo.infrastructure.dto.PriorityLevel;
import com.sajits.sajer.samo.infrastructure.http.HttpRequestSender;
import com.sajits.sajer.samo.infrastructure.http.OkHttpRequestSender;
import com.sajits.sajer.samo.infrastructure.impl.normalize.ObjectNormalizer;
import com.sajits.sajer.samo.infrastructure.interfaces.Encrypter;
import com.sajits.sajer.samo.infrastructure.interfaces.Normalizer;
import com.sajits.sajer.samo.infrastructure.interfaces.Signatory;

public class ApiConfig {

    private HttpRequestSender httpRequestSender;

    private Normalizer normalizer;

    private Encrypter encrypter;

    private Signatory signatory;

    private String baseUrl;

    private String apiVersion;

    private PriorityLevel priorityLevel;

    public ApiConfig() {
        this.httpRequestSender = new OkHttpRequestSender();
        this.normalizer = new ObjectNormalizer();
        this.baseUrl = "https://tp.tax.gov.ir/req/api/self-tsp";
        this.apiVersion = null;
        this.priorityLevel = PriorityLevel.NORMAL;
    }

    public ApiConfig httpRequestSender(HttpRequestSender httpRequestSender) {
        this.httpRequestSender = httpRequestSender;
        return this;
    }

    public ApiConfig normalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
        return this;
    }

    public ApiConfig encrypter(Encrypter encrypter) {
        this.encrypter = encrypter;
        return this;
    }

    public ApiConfig signatory(Signatory signatory) {
        this.signatory = signatory;
        return this;
    }

    public ApiConfig baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ApiConfig apiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public ApiConfig priorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public HttpRequestSender getHttpRequestSender() {
        return httpRequestSender;
    }

    public Normalizer getNormalizer() {
        return normalizer;
    }

    public Encrypter getEncrypter() {
        return encrypter;
    }

    public Signatory getSignatory() {
        return signatory;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }
}
