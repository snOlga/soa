package soa.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import soa.models.DTO.HumanDTO;
import soa.models.entity.HumanEntity;

@Service
@Mapper(componentModel = "spring", uses = { CoordinatesMapper.class, CarMapper.class })
public interface HumanMapper {
    HumanDTO toDTO(HumanEntity entity);

    @Mapping(ignore = true, target = "isDeleted")
    HumanEntity toEntity(HumanDTO dto);
}
