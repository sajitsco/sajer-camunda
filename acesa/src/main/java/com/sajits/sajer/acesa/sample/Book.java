package com.sajits.sajer.acesa.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    private Long id;
    private String name;
    private String newCol;

    public String getNewCol() {
        return newCol;
    }
    public void setNewCol(String newCol) {
        this.newCol = newCol;
    }
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Book() {
    }
 
    public Book(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    // standard constructors

    // standard getters and setters
}
