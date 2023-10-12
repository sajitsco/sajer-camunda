package com.sajits.sajer.core.entities;

public class FieldOption {
    private String type;
    private String label;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    private Object value;
    private String icon;
}
