package ejb.service.mapper;

import ejb.service.DTO.TeamDTO;
import ejb.service.entity.TeamEntity;

public class TeamMapper {
    
    public TeamDTO toDTO(TeamEntity entity) {
        return new TeamDTO(entity.getId(), entity.getName(), entity.getHumans(), entity.getIsDeleted());
    }

    public TeamEntity toEntity(TeamDTO dto) {
        return new TeamEntity(dto.getId(), dto.getName(), dto.getHumans(), dto.getIsDeleted());
    }
}
