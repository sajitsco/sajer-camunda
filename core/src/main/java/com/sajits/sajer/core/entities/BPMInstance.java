package com.sajits.sajer.core.entities;

public abstract class BPMInstance {
    private DataStructure dataStructure;

    public DataStructure getDataStructure() {
        return dataStructure;
    }

    public abstract void fillDataStructure();
    
}
