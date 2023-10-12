package com.sajits.sajer.core.auth.setting;

import java.util.HashMap;
import java.util.Map;

public class Section {
    private String id;
    private String label;
    private String icon;
    private Map<String, Field> fields;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Map<String, Field> getFields() {
        return fields;
    }
    public void setFields(HashMap<String, Field> fields) {
        this.fields = fields;
    }
}
