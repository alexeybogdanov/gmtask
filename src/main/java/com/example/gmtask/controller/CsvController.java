package com.example.gmtask.controller;

import com.example.gmtask.dto.ItemDto;
import com.example.gmtask.error.CsvItemNotFoundException;
import com.example.gmtask.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvController {

    private final CsvService csvService;

    @PostMapping
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        csvService.save(file);
        return new ResponseEntity<>("CSV file uploaded and saved successfully.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ItemDto> getItemByCode(@RequestParam(name = "code") String code) {
        var itemDto = csvService.getItemById(code).orElseThrow(
                () -> new CsvItemNotFoundException(String.format("Csv Item with code '%s' not found.", code)));

        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDto>> getAll() {
        List<ItemDto> itemDtos = csvService.fetchAll();
        if (itemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllItems() {
        csvService.deleteAll();

        return new ResponseEntity<>("All Csv Items deleted", HttpStatus.OK);
    }
}
