package com.csvconverter.validator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvValidatorService {

    public boolean isValidType(MultipartFile file) {
        return "text/csv".equalsIgnoreCase(file.getContentType());
    }


}
