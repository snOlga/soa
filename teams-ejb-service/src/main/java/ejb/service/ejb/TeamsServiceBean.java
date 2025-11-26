package ejb.service.ejb;

import ejb.service.DTO.HumanDTO;
import ejb.service.DTO.TeamDTO;
import ejb.service.ejb.i.TeamsService;
import ejb.service.entity.TeamEntity;
import ejb.service.exception.HumanAlreadyInTeamException;
import ejb.service.exception.HumanNotFoundException;
import ejb.service.exception.TeamNotFoundException;
import ejb.service.mapper.TeamMapper;
import ejb.service.repository.TeamRepository;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.web.client.RestTemplate;

@Stateless(name = "TeamsServiceBean")
public class TeamsServiceBean implements TeamsService {

    @Inject
    TeamRepository repo;
    TeamMapper mapper = new TeamMapper();
    @Inject
    RestTemplate restTemplate;
    @ConfigProperty(name = "urls.human-service")
    String humanServiceUrl;

    @Override
    public TeamDTO get(Long id) {
        System.err.println("HIIIII");
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        removeAllDeletedHumans(id);
        return mapper.toDTO(repo.findById(id));
    }

    @Override
    public TeamDTO create(TeamDTO dto) {
        for (Long humanId : dto.getHumans()) {
            if (getHuman(humanId) == null)
                throw new HumanNotFoundException();
        }
        TeamEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public TeamDTO delete(Long id) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        TeamEntity entity = repo.findById(id);
        entity.setIsDeleted(true);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public TeamDTO add(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        removeAllDeletedHumans(teamId);
        TeamEntity entity = repo.findById(teamId);

        if (entity.getHumans().contains(humanId))
            throw new HumanAlreadyInTeamException();
        entity.getHumans().add(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public TeamDTO deleteMember(Long teamId, Long humanId) {
        checkTeamAndHuman(teamId, humanId);
        removeAllDeletedHumans(teamId);
        TeamEntity entity = repo.findById(teamId);

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
        TeamEntity entity = repo.findById(teamId);
        entity.setHumans(entity.getHumans().stream().filter(humanId -> {
            try {
                getHuman(humanId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList()));
        repo.save(entity);
    }
}
