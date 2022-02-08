package com.csvconverter.service;

import com.csvconverter.dto.CsvDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CsvService {

    void uploadFile(MultipartFile file) throws IOException;

    List<CsvDTO> getAll();

    CsvDTO findByCode(String code);

    void deleteAll();
}
