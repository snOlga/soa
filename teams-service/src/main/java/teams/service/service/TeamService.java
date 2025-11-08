package teams.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import soa.models.DTO.HumanDTO;
import soa.models.exception.HumanNotFoundException;
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
    RestTemplate restTemplate;
    @Value("${urls.human-service}")
    String humanServiceUrl;

    public TeamDTO get(Long id) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        removeAllDeletedHumans(id);
        return mapper.toDTO(repo.findById(id).orElse(new TeamEntity()));
    }

    public TeamDTO create(TeamDTO dto) {
        for (Long humanId : dto.getHumans()) {
            if (getHuman(humanId) == null)
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
        removeAllDeletedHumans(teamId);
        TeamEntity entity = repo.findById(teamId).get();

        if (entity.getHumans().contains(humanId))
            throw new HumanAlreadyInTeamException();
        entity.getHumans().add(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    public TeamDTO deleteMember(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        removeAllDeletedHumans(teamId);
        TeamEntity entity = repo.findById(teamId).get();

        if (!entity.getHumans().contains(humanId))
            throw new TeamNotFoundException();
        entity.getHumans().remove(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    private void checkTeamAndHuman(Long teamId, Long humanId) {
        if (!repo.existsById(teamId))
            throw new TeamNotFoundException();
        HumanDTO a = getHuman(humanId);
        if (a == null)
            throw new HumanNotFoundException();
    }

    private HumanDTO getHuman(Long id) {
        return restTemplate.getForObject(humanServiceUrl + "/" + id, HumanDTO.class);
    }

    private void removeAllDeletedHumans(Long teamId) {
        TeamEntity entity = repo.findById(teamId).get();
        entity.setHumans(entity.getHumans().stream().filter(humanId -> {
            try {
                getHuman(humanId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }).toList());
        repo.save(entity);
    }
}
