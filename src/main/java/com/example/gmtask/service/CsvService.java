package com.example.gmtask.service;

import com.example.gmtask.dto.ItemDto;
import com.example.gmtask.entity.CsvItem;
import com.example.gmtask.error.CsvParseException;
import com.example.gmtask.helper.CsvHelper;
import com.example.gmtask.repository.CsvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CsvService {

    private final CsvRepository repository;

    public void save(MultipartFile file) {
        Set<CsvItem> csvCsvItems = readItemsFromFile(file);
        repository.saveAll(csvCsvItems);
    }

    public Optional<ItemDto> getItemById(String code) {
        return repository.findById(code).map(this::mapItemToDto);
    }

    public List<ItemDto> fetchAll() {
        List<CsvItem> csvItems = repository.findAll();
        return csvItems.stream()
                .map(this::mapItemToDto)
                .toList();
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    private ItemDto mapItemToDto(CsvItem csvItem) {
        return new ItemDto(
                csvItem.getCode(),
                csvItem.getSource(),
                csvItem.getCodeListCode(),
                csvItem.getDisplayValue(),
                csvItem.getLongDescription(),
                csvItem.getFromDate(),
                csvItem.getToDate(),
                csvItem.getSortingPriority()
        );
    }

    private Set<CsvItem> readItemsFromFile(MultipartFile file) {
        try {
            return CsvHelper.readAndConvertToCsvItem(file.getInputStream());
        } catch (IOException e) {
            throw new CsvParseException("Failed to read and parse the CSV file: " + e.getMessage());
        }
    }
}
