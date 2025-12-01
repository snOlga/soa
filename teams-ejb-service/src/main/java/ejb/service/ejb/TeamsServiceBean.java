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

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless(name = "TeamsService")
public class TeamsServiceBean implements TeamsService {

    @Inject
    TeamRepository repo;
    TeamMapper mapper = new TeamMapper();
    private Client client;
    String humanServiceUrl = "https://localhost:18018/humans";

    @PostConstruct
    public void init() {
        this.client = ClientBuilder.newClient();
    }

    private String sendGetRequest(Long id) {
        String targetUrl = "https://host.docker.internal:18018/humans/" + id;

        try (Response response = client.target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .get()) {

            if (response.getStatus() == 200) {
                return response.readEntity(String.class);
            } else {
                return "Error: " + response.getStatus();
            }
        }
    }

    @Override
    public TeamDTO get(Long id) {
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
        System.err.println(sendGetRequest(id));
        return null;
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
