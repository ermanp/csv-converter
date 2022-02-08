package com.csvconverter.service.impl;

import com.csvconverter.dto.CsvDTO;
import com.csvconverter.model.Csv;
import com.csvconverter.repository.CsvRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CsvServiceImplTest {

    @InjectMocks
    private CsvServiceImpl csvService;
    @Mock
    private CsvRepository csvRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void importData() {
        Csv csv = new Csv("ZIB","ZIB001","271636001","Polsslag regelmatig",
                "The long description is necessary","01-01-2019","","1");
        List<Csv> csvList = List.of(csv);

        Mockito.when(csvRepository.saveAll(csvList)).thenReturn(csvList);

        csvService.importData(csvList);
        Mockito.verify(csvRepository).saveAll(csvList);

    }

    @Test
    void getAll() {
        Csv csv1 = new Csv("ZIB","ZIB001","271636001","Polsslag regelmatig",
                "The long description is necessary","01-01-2019","","1");
        Csv csv2 = new Csv("ZIB","ZIB001","61086009","Polsslag onregelmatig",
                "","01-01-2019","","2");
        Mockito.when(csvRepository.findAll()).thenReturn(Arrays.asList(csv1,csv2));
        List<CsvDTO> dtoList = csvService.getAll();
        assertEquals(2,dtoList.size());

    }

    @Test
    void findByCode() {
        Csv csv = new Csv("ZIB","ZIB001","271636001","Polsslag regelmatig",
                "The long description is necessary","01-01-2019","","1");
        Mockito.when(csvRepository.findByCode("ZIB001")).thenReturn(csv);
        CsvDTO csvDto = csvService.findByCode("ZIB001");
        assertEquals(csvDto.getCode(),csv.getCode());
    }
}