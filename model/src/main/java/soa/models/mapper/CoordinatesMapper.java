package soa.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import soa.models.DTO.CoordinatesDTO;
import soa.models.entity.CoordinatesEntity;

@Service
@Mapper(componentModel = "spring")
public interface CoordinatesMapper {
    CoordinatesDTO toDTO(CoordinatesEntity entity);

    @Mapping(ignore = true, target = "isDeleted")
    CoordinatesEntity toEntity(CoordinatesDTO dto);
}
