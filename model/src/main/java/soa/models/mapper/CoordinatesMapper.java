package soa.models.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import soa.models.DTO.CoordinatesDTO;
import soa.models.entity.CoordinatesEntity;

@Component
public class CoordinatesMapper {

    private final ModelMapper mapper = new ModelMapper();

    @PostConstruct
    private void configureMappings() {
        TypeMap<CoordinatesEntity, CoordinatesDTO> toDtoTypeMap = mapper.createTypeMap(CoordinatesEntity.class, CoordinatesDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(CoordinatesDTO::setIsDeleted);
        });

        TypeMap<CoordinatesDTO, CoordinatesEntity> toEntityTypeMap = mapper.createTypeMap(CoordinatesDTO.class, CoordinatesEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(CoordinatesEntity::setId);
            m.skip(CoordinatesEntity::setIsDeleted);
        });
    }

    public CoordinatesDTO toDTO(CoordinatesEntity entity) {
        return mapper.map(entity, CoordinatesDTO.class);
    }

    public CoordinatesEntity toEntity(CoordinatesDTO dto) {
        return mapper.map(dto, CoordinatesEntity.class);
    }
}
