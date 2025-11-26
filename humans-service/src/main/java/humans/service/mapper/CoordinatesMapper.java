package humans.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import humans.service.DTO.CoordinatesDTO;
import humans.service.entity.CoordinatesEntity;

@Component
public class CoordinatesMapper {

    private final ModelMapper mapper;

    public CoordinatesMapper() {
        this.mapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        mapper.createTypeMap(CoordinatesEntity.class, CoordinatesDTO.class);

        TypeMap<CoordinatesDTO, CoordinatesEntity> toEntityTypeMap = mapper.createTypeMap(CoordinatesDTO.class,
                CoordinatesEntity.class);
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
