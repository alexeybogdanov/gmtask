package com.example.gmtask.dto;

import java.time.LocalDate;

public record ItemDto(
        String code,
        String source,
        String codeListCode,
        String displayValue,
        String longDescription,
        LocalDate fromDate,
        LocalDate toDate,
        String sortingPriority) {
}
