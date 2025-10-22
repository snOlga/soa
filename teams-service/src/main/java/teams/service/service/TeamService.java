package teams.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soa.models.exception.HumanNotFoundException;
import soa.models.repository.HumanRepository;
import teams.service.dto.TeamDTO;
import teams.service.entity.TeamEntity;
import teams.service.exception.HumanAlreadyInTeamException;
import teams.service.exception.TeamNotFoundException;
import teams.service.mapper.TeamMapper;
import teams.service.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    TeamRepository repo;
    @Autowired
    TeamMapper mapper;
    @Autowired
    HumanRepository humanRepository;

    public TeamDTO get(Long id) {
        return mapper.toDTO(repo.findById(id).orElse(new TeamEntity()));
    }

    public TeamDTO create(TeamDTO dto) {
        for (Long humanId : dto.getHumans()) {
            if (!humanRepository.existsById(humanId))
                throw new HumanNotFoundException();
        }
        TeamEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    public TeamDTO delete(Long id) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        TeamEntity entity = repo.findById(id).get();
        entity.setIsDeleted(true);
        return mapper.toDTO(repo.save(entity));
    }

    public TeamDTO add(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        TeamEntity entity = repo.findById(teamId).get();

        if (entity.getHumans().contains(humanId))
            throw new HumanAlreadyInTeamException();
        entity.getHumans().add(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    public TeamDTO deleteMember(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        TeamEntity entity = repo.findById(teamId).get();

        if (!entity.getHumans().contains(humanId))
            throw new TeamNotFoundException();
        entity.getHumans().remove(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    private void checkTeamAndHuman(Long teamId, Long humanId) {
        if (!repo.existsById(teamId))
            throw new TeamNotFoundException();
        if (!humanRepository.existsById(humanId))
            throw new HumanNotFoundException();
    }
}
