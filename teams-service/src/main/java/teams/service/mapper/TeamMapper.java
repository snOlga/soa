package teams.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import teams.service.dto.TeamDTO;
import teams.service.entity.TeamEntity;

@Component
public class TeamMapper {

    private final ModelMapper mapper;

    public TeamMapper() {
        this.mapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        TypeMap<TeamEntity, TeamDTO> toDtoTypeMap = mapper.createTypeMap(TeamEntity.class, TeamDTO.class);
        toDtoTypeMap.addMappings(m -> {
            m.skip(TeamDTO::setIsDeleted);
        });

        TypeMap<TeamDTO, TeamEntity> toEntityTypeMap = mapper.createTypeMap(TeamDTO.class, TeamEntity.class);
        toEntityTypeMap.addMappings(m -> {
            m.skip(TeamEntity::setId);
            m.skip(TeamEntity::setIsDeleted);
        });
    }

    public TeamDTO toDTO(TeamEntity entity) {
        return mapper.map(entity, TeamDTO.class);
    }

    public TeamEntity toEntity(TeamDTO dto) {
        return mapper.map(dto, TeamEntity.class);
    }
}
