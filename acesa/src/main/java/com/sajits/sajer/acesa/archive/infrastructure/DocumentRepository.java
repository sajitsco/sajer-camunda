package com.sajits.sajer.acesa.archive.infrastructure;

import com.sajits.sajer.acesa.archive.domain.Document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}