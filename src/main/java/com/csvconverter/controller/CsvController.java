package com.csvconverter.controller;

import com.csvconverter.dto.CsvDTO;
import com.csvconverter.exception.InvalidFileTypeException;
import com.csvconverter.service.CsvService;
import com.csvconverter.validator.CsvValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CsvController {

    private final CsvService csvService;
    private final CsvValidatorService csvValidatorService;

    public CsvController(CsvService csvService, CsvValidatorService csvValidatorService) {
        this.csvService = csvService;
        this.csvValidatorService = csvValidatorService;
    }


    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        if (!csvValidatorService.isValidType(file)) {
            throw new InvalidFileTypeException();
        }
        try {
            csvService.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/fetch-all")
    public ResponseEntity<List<CsvDTO>> fetchAllData() {
        return ResponseEntity.ok(csvService.getAll());
    }


    @GetMapping("/fetch-by-code")
    public ResponseEntity<CsvDTO> fetchAllData(@RequestParam("code") String code) {
        return ResponseEntity.ok(csvService.findByCode(code));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllData() {
        csvService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
