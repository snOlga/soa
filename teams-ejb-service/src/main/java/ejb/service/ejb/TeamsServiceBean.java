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
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.naming.spi.ObjectFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@WebService(serviceName = "TeamsService", portName = "TeamsServicePort", name = "TeamsService", targetNamespace = "http://my.service/", endpointInterface = "ejb.service.ejb.i.TeamsService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
        ObjectFactory.class
})
public class TeamsServiceBean implements TeamsService {

    @Autowired
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
        String targetUrl = "http://172.20.0.19:9018/humans/" + id;

        try (Response response = client.target(targetUrl)
                .request(MediaType.APPLICATION_JSON)
                .get()) {
            return response.readEntity(HumanDTO.class);
        }
    }

    @WebMethod(operationName = "getTeam")
    @WebResult(name = "team")
    @Override
    public TeamDTO get(
        @WebParam(name = "id") Long id
    ) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        return mapper.toDTO(repo.findById(id).get());
    }

    @WebMethod(operationName = "createTeam")
    @WebResult(name = "team")
    @Override
    public TeamDTO create(
        @WebParam(name = "team") TeamDTO dto
    ) {
        for (Long humanId : dto.getHumans()) {
            if (getHuman(humanId) == null)
                throw new HumanNotFoundException();
        }
        TeamEntity entity = mapper.toEntity(dto);
        entity.setIsDeleted(false);
        return mapper.toDTO(repo.save(entity));
    }

    @WebMethod(operationName = "deleteTeam")
    @WebResult(name = "team")
    @Override
    public TeamDTO delete(
        @WebParam(name = "id") Long id
    ) {
        if (!repo.existsById(id))
            throw new TeamNotFoundException();
        TeamEntity entity = repo.findById(id).get();
        entity.setIsDeleted(true);
        return mapper.toDTO(repo.save(entity));
    }

    @WebMethod(operationName = "addMember")
    @WebResult(name = "team")
    @Override
    public TeamDTO add(
        @WebParam(name = "teamId") Long teamId, 
        @WebParam(name = "humanId") Long humanId
    ) {
        if (!repo.existsById(teamId))
            throw new TeamNotFoundException();
        TeamEntity entity = repo.findById(teamId).get();

        if (entity.getHumans().contains(humanId))
            throw new HumanAlreadyInTeamException();
        entity.getHumans().add(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    @WebMethod(operationName = "removeMemberFromTeam")
    @WebResult(name = "team")
    @Override
    public TeamDTO deleteMember(
        @WebParam(name = "teamId") Long teamId, 
        @WebParam(name = "humanId") Long humanId
    ) {
        if (!repo.existsById(teamId))
            throw new TeamNotFoundException();
        TeamEntity entity = repo.findById(teamId).get();

        if (!entity.getHumans().contains(humanId))
            throw new TeamNotFoundException();
        entity.getHumans().remove(humanId);
        return mapper.toDTO(repo.save(entity));
    }

    @WebMethod(operationName = "deleteMemberByHumanId")
    @Override
    public void deleteMemberByHumanId(
        @WebParam(name = "humanId") Long humanId
    ) {
        List<TeamEntity> teams = repo.findTeamsContainingHuman(humanId);
        for (TeamEntity team : teams) {
            if (team.getHumans().contains(humanId)) {
                team.getHumans().remove(humanId);
                repo.save(team);
            }
        }
    }

    private HumanDTO getHuman(Long id) {
        return sendGetRequest(id);
    }
}
