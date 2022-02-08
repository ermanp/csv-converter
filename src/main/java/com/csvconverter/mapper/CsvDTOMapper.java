package com.csvconverter.mapper;

import com.csvconverter.dto.CsvDTO;
import com.csvconverter.model.Csv;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CsvDTOMapper {
    CsvDTOMapper INSTANCE = Mappers.getMapper(CsvDTOMapper.class);

    Csv toCsv(CsvDTO csvInputDTO);

    CsvDTO toCsvInputDTO(Csv csv);
}
