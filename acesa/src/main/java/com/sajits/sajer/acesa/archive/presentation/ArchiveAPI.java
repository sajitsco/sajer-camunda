package com.sajits.sajer.acesa.archive.presentation;

import java.util.List;

import com.sajits.sajer.acesa.archive.domain.Document;
import com.sajits.sajer.acesa.archive.infrastructure.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/p/archive")
public class ArchiveAPI {

    @Autowired
    private DocumentRepository documentRepository; 

    @GetMapping("/document")
	ResponseEntity<List<Document>> getDocument(@RequestParam("id") String id) {
        return ResponseEntity.ok(documentRepository.findAll());
    }
}
