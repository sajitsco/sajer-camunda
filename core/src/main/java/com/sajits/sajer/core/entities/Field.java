package com.sajits.sajer.core.entities;

import java.util.List;

public class Field {
    private int index;
    private String cid;
    private String label;
    private FieldType type;
    private Boolean required;
    private List<FieldOption> options;
    private Object value;

public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    //------------------
    private boolean visible;
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }    
    private boolean editable;
    public boolean isEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    private boolean customizable;
    public boolean isCustomizable() {
        return customizable;
    }
    public void setCustomizable(boolean customizable) {
        this.customizable = customizable;
    }    
//------------------

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public FieldType getType() {
        return type;
    }
    public void setType(FieldType type) {
        this.type = type;
    }
    public Boolean getRequired() {
        return required;
    }
    public void setRequired(Boolean required) {
        this.required = required;
    }
    public List<FieldOption> getOptions() {
        return options;
    }
    public void setOptions(List<FieldOption> options) {
        this.options = options;
    }
}
