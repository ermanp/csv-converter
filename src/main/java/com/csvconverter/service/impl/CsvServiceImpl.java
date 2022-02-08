package com.csvconverter.service.impl;

import com.csvconverter.dto.CsvDTO;
import com.csvconverter.mapper.CsvDTOMapper;
import com.csvconverter.model.Csv;
import com.csvconverter.repository.CsvRepository;
import com.csvconverter.service.CsvService;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvServiceImpl implements CsvService {

    private final CsvRepository csvRepository;

    public CsvServiceImpl(CsvRepository csvRepository) {
        this.csvRepository = csvRepository;
    }


    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        List<CsvDTO> dtoList = getDataFromFile(file);

        this.importData(dtoList.stream()
                .map(CsvDTOMapper.INSTANCE::toCsv)
                .collect(Collectors.toList()));
    }

    public List<CsvDTO> getDataFromFile(MultipartFile file) throws IOException {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            return new CsvToBeanBuilder(reader)
                    .withType(CsvDTO.class)
                    .build()
                    .parse();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return new ArrayList<>();
    }

    public void importData(List<Csv> csvList) {
        csvRepository.saveAll(csvList);
    }

    @Override
    public List<CsvDTO> getAll() {
        List<Csv> allData = csvRepository.findAll();
        return allData.stream()
                .map(CsvDTOMapper.INSTANCE::toCsvInputDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CsvDTO findByCode(String code) {
        Csv csv = csvRepository.findByCode(code);
        return CsvDTOMapper.INSTANCE.toCsvInputDTO(csv);
    }

    @Override
    public void deleteAll() {
        csvRepository.deleteAll();
    }
}
