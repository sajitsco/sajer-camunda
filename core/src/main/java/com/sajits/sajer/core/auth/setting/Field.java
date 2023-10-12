package com.sajits.sajer.core.auth.setting;

import java.util.List;

public class Field {
    private String label;
    private String type;
    private Object value;
    private List<Object> options;
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public List<Object> getOptions() {
        return options;
    }
    public void setOptions(List<Object> options) {
        this.options = options;
    }
}
