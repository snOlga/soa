package humans.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import humans.service.DTO.CarDTO;
import humans.service.entity.CarEntity;

@Component
public class CarMapper {

    private final ModelMapper mapper;

    public CarMapper() {
        this.mapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        mapper.createTypeMap(CarEntity.class, CarDTO.class);

        TypeMap<CarDTO, CarEntity> toEntityTypeMap = mapper.createTypeMap(CarDTO.class, CarEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(CarEntity::setId);
            m.skip(CarEntity::setIsDeleted);
        });
    }

    public CarDTO toDTO(CarEntity entity) {
        return mapper.map(entity, CarDTO.class);
    }

    public CarEntity toEntity(CarDTO dto) {
        return mapper.map(dto, CarEntity.class);
    }
}
