package soa.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import soa.models.DTO.CarDTO;
import soa.models.entity.CarEntity;

@Service
@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDTO(CarEntity entity);

    @Mapping(ignore = true, target = "isDeleted")
    CarEntity toEntity(CarDTO dto);
}
