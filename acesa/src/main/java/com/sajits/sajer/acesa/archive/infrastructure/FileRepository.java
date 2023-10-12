package com.sajits.sajer.acesa.archive.infrastructure;

import com.sajits.sajer.acesa.archive.domain.File;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}