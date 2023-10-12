package com.sajits.sajer.acesa.archive.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sajits.sajer.acesa.archive.infrastructure.StringToListConverter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Document {
    private UUID id;
    private String title = "";
    private String documentNumber = "";

    @Column
    @ElementCollection(targetClass=Document.class)
    private List<File> files = new ArrayList<>();
    private List<String> categories = new ArrayList<>();;
    private List<String> referenceList = new ArrayList<>();;
    private String sender = "";
    private String description = "";
    private String physicalRepo = "";
    private List<String> docLinks = new ArrayList<>();
    private Date documentDate = new Date();
    
    private Date createDateTime;
    @CreationTimestamp
    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    private Date updateDateTime;
    @UpdateTimestamp
    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }


    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @OneToMany
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Convert(converter = StringToListConverter.class)
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Convert(converter = StringToListConverter.class)
    public List<String> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<String> referenceList) {
        this.referenceList = referenceList;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhysicalRepo() {
        return physicalRepo;
    }

    public void setPhysicalRepo(String physicalRepo) {
        this.physicalRepo = physicalRepo;
    }

    @Convert(converter = StringToListConverter.class)
    public List<String> getDocLinks() {
        return docLinks;
    }

    public void setDocLinks(List<String> docLinks) {
        this.docLinks = docLinks;
    }

    public Document() {
        this.id = UUID.randomUUID();

    }

    public Document(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
