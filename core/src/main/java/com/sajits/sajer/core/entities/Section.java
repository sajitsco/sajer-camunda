package com.sajits.sajer.core.entities;

import java.util.Map;

public class Section {
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
    private int index;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    private String label;    

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
    public void setFields(Map<String, Field> fields) {
        this.fields = fields;
    }
    private String icon;
    private Map<String, Field> fields;
}
