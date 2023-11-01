package com.example.gmtask.controller;

import com.example.gmtask.entity.CsvItem;
import com.example.gmtask.helper.CsvHelper;
import com.example.gmtask.repository.CsvRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CsvControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CsvRepository repository;

    @BeforeEach
    void setUp() {
        InputStream inputStream = this.getClass().getResourceAsStream("/exercise.csv");
        Set<CsvItem> csvItems = CsvHelper.readAndConvertToCsvItem(inputStream);
        repository.saveAll(csvItems);
    }

    @Test
    public void testUploadCsvFile() throws Exception {

        InputStream inputStream = this.getClass().getResourceAsStream("/exercise.csv");

        MockMultipartFile file = new MockMultipartFile("file", "sample.csv", "text/csv", inputStream);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/csv")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("CSV file uploaded and saved successfully."));
    }

    @Test
    public void testGetItemByCode() throws Exception {

        String itemCode = "271636001";

        mockMvc.perform(MockMvcRequestBuilders.get("/csv")
                        .param("code", itemCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(itemCode));
    }

    @Test
    public void testGetItemByCode_notFound() throws Exception {

        String itemCode = "Non existing item";

        mockMvc.perform(MockMvcRequestBuilders.get("/csv")
                        .param("code", itemCode))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/csv/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testDeleteAllItems() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/csv"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("All Csv Items deleted"));
    }
}
