package teams.service.client;

import ejb.service.DTO.TeamDTO;
import ejb.service.ejb.i.TeamsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MuleEsbTeamsService implements TeamsService {

    @Value("${muleesb.url:http://host.docker.internal:8081}")
    private String muleEsbUrl;

    private final RestTemplate restTemplate;

    public MuleEsbTeamsService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public TeamDTO get(Long id) {
        try {
            String url = muleEsbUrl + "/teams/" + id;
            ResponseEntity<TeamDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(null),
                    TeamDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error calling MuleESB: " + e.getMessage(), e);
        }
    }

    @Override
    public TeamDTO create(TeamDTO dto) {
        try {
            String url = muleEsbUrl + "/teams";
            ResponseEntity<TeamDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    createHttpEntity(dto),
                    TeamDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error calling MuleESB: " + e.getMessage(), e);
        }
    }

    @Override
    public TeamDTO delete(Long id) {
        try {
            String url = muleEsbUrl + "/teams/" + id;
            ResponseEntity<TeamDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    createHttpEntity(null),
                    TeamDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error calling MuleESB: " + e.getMessage(), e);
        }
    }

    @Override
    public TeamDTO add(Long teamId, Long humanId) {
        try {
            String url = muleEsbUrl + "/teams/" + teamId + "/members/" + humanId;
            ResponseEntity<TeamDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    createHttpEntity(null),
                    TeamDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error calling MuleESB: " + e.getMessage(), e);
        }
    }

    @Override
    public TeamDTO deleteMember(Long teamId, Long humanId) {
        try {
            String url = muleEsbUrl + "/teams/" + teamId + "/members/" + humanId;
            ResponseEntity<TeamDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    createHttpEntity(null),
                    TeamDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error calling MuleESB: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteMemberByHumanId(Long humanId) {
        try {
            String url = muleEsbUrl + "/teams/members/" + humanId;
            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    createHttpEntity(null),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error calling MuleESB: " + e.getMessage(), e);
        }
    }

    private HttpEntity<Object> createHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }
}

