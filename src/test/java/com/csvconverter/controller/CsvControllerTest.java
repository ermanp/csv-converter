package com.csvconverter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.csvconverter.dto.CsvDTO;
import com.csvconverter.service.CsvService;
import com.csvconverter.validator.CsvValidatorService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CsvController.class)
class CsvControllerTest {

    private final static String CONTENT_TYPE = "application/json";

    @InjectMocks
    private CsvController csvController;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CsvService csvService;
    @MockBean
    private CsvValidatorService csvValidatorService;

    @Before("")
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(this.csvValidatorService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(csvController).build();
    }


    @Test
    void uploadFile() throws Exception {
        MockMultipartFile csvFile = new MockMultipartFile("data", "file.csv", "text/csv", "some csv".getBytes());
        Mockito.when(csvValidatorService.isValidType(any(MultipartFile.class))).thenReturn(true);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                .file("file", csvFile.getBytes())
                .content("csvValidatorService.isValidType(csvFile)")
        );

        ArgumentCaptor<MultipartFile> captor = ArgumentCaptor.forClass(MultipartFile.class);
        Mockito.verify(csvValidatorService, times(1)).isValidType(any(MultipartFile.class));
        Mockito.verify(csvService, times(1)).uploadFile(captor.capture());
        actions.andExpect(status().isOk());

    }

    @Test
    void whenInvalidMimeType_thenReturns406() throws Exception {

        MockMultipartFile docFile = new MockMultipartFile("data", "file-name.doc", "application/msword", "some other type".getBytes());


        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file("file", docFile.getBytes())
                        .param("some-random", "4"))
                .andExpect(status().is(406));

    }

    @Test
    void whenCallFetchAll_thenReturn200() throws Exception {
        CsvDTO dto = new CsvDTO("ZIB","ZIB001","271636001","Polsslag regelmatig",
                "The long description is necessary","01-01-2019","","1");
        when(csvService.getAll()).thenReturn(List.of(dto));

        MvcResult mvcResult = mockMvc.perform(get("/fetch-all")
                .accept(CONTENT_TYPE)).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Mockito.verify(csvService, times(1)).getAll();
        assertEquals(objectMapper.writeValueAsString(List.of(dto)).trim(),responseBody);

    }

    @Test
    void whenCallFetchAll_thenReturnNoData() throws Exception {
        when(csvService.getAll()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get("/fetch-all")
                .accept(CONTENT_TYPE)).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Mockito.verify(csvService, times(1)).getAll();
        assertEquals(objectMapper.writeValueAsString(Collections.emptyList()).trim(),responseBody);

    }

    @Test
    void whenCallFetchByCode_thenReturns200() throws Exception {
        CsvDTO dto = new CsvDTO("ZIB","ZIB001","271636001","Polsslag regelmatig",
                "The long description is necessary","01-01-2019","","1");
        Mockito.when(csvService.findByCode(anyString())).thenReturn(dto);

        MvcResult mvcResult = mockMvc.perform(get("/fetch-by-code")
                .param("code", "code")
                .accept(CONTENT_TYPE)).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Mockito.verify(csvService, times(1)).findByCode(anyString());
        assertEquals(objectMapper.writeValueAsString(dto).trim(),responseBody);
    }
}