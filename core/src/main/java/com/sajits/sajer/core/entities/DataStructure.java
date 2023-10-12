package com.sajits.sajer.core.entities;

import java.util.Map;

public class DataStructure {
    private Map<String, Section> sections;
    public Map<String, Section> getSections() {
        return sections;
    }
    public void setSections(Map<String, Section> sections) {
        this.sections = sections;
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
}
