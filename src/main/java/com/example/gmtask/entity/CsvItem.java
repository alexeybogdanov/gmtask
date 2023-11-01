package com.example.gmtask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvItem {

    @Id
    private String code;

    @Column
    private String source;

    @Column
    private String codeListCode;

    @Column
    private String displayValue;

    @Column
    private String longDescription;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column
    private String sortingPriority;
}
