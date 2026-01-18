package ejb.service.ejb.i;

import ejb.service.DTO.TeamDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface TeamsService {
    @WebMethod
    public TeamDTO getTeam(Long id);

    @WebMethod
    public TeamDTO createTeam(TeamDTO dto);

    @WebMethod
    public TeamDTO deleteTeam(Long id);

    @WebMethod
    public TeamDTO addMember(Long teamId, Long humanId);

    @WebMethod
    public TeamDTO removeMemberFromTeam(Long teamId, Long humanId);

    @WebMethod
    public void deleteMemberByHumanId(Long humanId);
}