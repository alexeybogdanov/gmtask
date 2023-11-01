package com.example.gmtask.helper;


import com.example.gmtask.entity.CsvItem;
import com.example.gmtask.error.CsvParseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class CsvHelper {

    private final static String[] HEADERS = {"source", "codeListCode", "code", "displayValue",
            "longDescription", "fromDate", "toDate", "sortingPriority"};

    public static Set<CsvItem> readAndConvertToCsvItem(InputStream inputStream) {
        try {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> csvRecords = csvFormat.parse(new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8)));

            return convertToCsvItem(csvRecords);

        } catch (IOException e) {
            throw new CsvParseException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static Set<CsvItem> convertToCsvItem(Iterable<CSVRecord> csvRecords) {
        return StreamSupport.stream(csvRecords.spliterator(), false)
                .map(csvRecord -> {
                    CsvItem csvItem = new CsvItem();
                    csvItem.setSource(csvRecord.get("source"));
                    csvItem.setCode(csvRecord.get("code"));
                    csvItem.setCodeListCode(csvRecord.get("codeListCode"));
                    csvItem.setDisplayValue(csvRecord.get("displayValue"));
                    csvItem.setLongDescription(csvRecord.get("longDescription"));
                    csvItem.setFromDate(parseDate(csvRecord.get("fromDate")));
                    csvItem.setFromDate(parseDate(csvRecord.get("fromDate")));
                    csvItem.setSortingPriority(csvRecord.get("sortingPriority"));
                    return csvItem;
                })
                .collect(Collectors.toSet());
    }

    private static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeException e) {
            throw new CsvParseException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
