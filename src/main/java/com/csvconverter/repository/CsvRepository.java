package com.csvconverter.repository;

import com.csvconverter.model.Csv;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsvRepository extends CrudRepository<Csv, Long> {

    List<Csv> findAll();

    Csv findByCode(String code);
}
