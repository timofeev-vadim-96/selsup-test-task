package ru.selsup.jobTestTask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.selsup.jobTestTask.model.Document;
import ru.selsup.jobTestTask.service.DocumentService;


@RestController
@RequestMapping("/api/v3/lk/documents/")
@RequiredArgsConstructor
public class CrptApi {
    private final DocumentService service;

    @Operation(summary = "add new document")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping("create")
    public ResponseEntity<Document> createDocument(@RequestBody Document document, @RequestParam("sign") String sign){
        Document savedDocument = service.saveDocument(document, sign);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }
}
