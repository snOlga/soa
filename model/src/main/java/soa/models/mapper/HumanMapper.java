package soa.models.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import soa.models.DTO.HumanDTO;
import soa.models.entity.HumanEntity;

@Component
public class HumanMapper {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CoordinatesMapper coordinatesMapper;
    private final ModelMapper mapper;

    public HumanMapper() {
        this.mapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        mapper.createTypeMap(HumanEntity.class, HumanDTO.class);

        TypeMap<HumanDTO, HumanEntity> toEntityTypeMap = mapper.createTypeMap(HumanDTO.class, HumanEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(HumanEntity::setId);
            m.skip(HumanEntity::setCreationDate);
            m.skip(HumanEntity::setIsDeleted);
        });
    }

    public HumanDTO toDTO(HumanEntity entity) {
        HumanDTO dto = mapper.map(entity, HumanDTO.class);
        if (entity.getCar() != null)
            dto.setCar(carMapper.toDTO(entity.getCar()));
        dto.setCoordinates(coordinatesMapper.toDTO(entity.getCoordinates()));
        return dto;
    }

    public HumanEntity toEntity(HumanDTO dto) {
        HumanEntity entity = mapper.map(dto, HumanEntity.class);
        if (dto.getCar() != null)
            entity.setCar(carMapper.toEntity(dto.getCar()));
        entity.setCoordinates(coordinatesMapper.toEntity(dto.getCoordinates()));
        return entity;
    }
}
