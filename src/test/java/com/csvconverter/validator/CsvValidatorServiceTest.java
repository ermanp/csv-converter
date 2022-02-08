package com.csvconverter.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CsvValidatorServiceTest {

    @InjectMocks
    private CsvValidatorService csvValidatorService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void validType_Test() {
        MockMultipartFile csvFile = new MockMultipartFile("data", "file.csv", "text/csv", "some csv".getBytes());
        boolean isValid = csvValidatorService.isValidType(csvFile);
        assertTrue(isValid);
    }

    @Test
    void invalidType_Test() {
        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        boolean isValid = csvValidatorService.isValidType(jsonFile);
        assertFalse(isValid);
    }
}