package com.example.gmtask.repository;

import com.example.gmtask.entity.CsvItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvRepository extends JpaRepository<CsvItem, String> {
}
