package com.sajits.sajer.acesa.archive.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class File {
    
    private long id = 0;
    private String name = "";
    private String path = "";
    private String mark = "";
    private String footprint = "";

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public String getName() {
        return name;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public File() {
    }
    public void setPath(String path) {
        this.path = path;
    }
}
