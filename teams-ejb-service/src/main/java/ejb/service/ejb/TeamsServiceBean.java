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

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.util.stream.Collectors;

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
    Client client = createUnsafeClient();

    private Client createUnsafeClient() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

        } catch (Exception ignore) {
        }

        return ClientBuilder.newBuilder()
                .sslContext(sc)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    private HumanDTO sendGetRequest(Long id) {
        String targetUrl = "https://host.docker.internal:18018/humans/" + id;

        try (Response response = client.target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .get()) {
            return response.readEntity(HumanDTO.class);
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
        entity.setIsDeleted(false);
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
        return sendGetRequest(id);
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
