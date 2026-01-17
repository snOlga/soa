package ejb.service.ejb.i;

import ejb.service.DTO.TeamDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface TeamsService {
    @WebMethod
    public TeamDTO get(Long id);

    @WebMethod
    public TeamDTO create(TeamDTO dto);

    @WebMethod
    public TeamDTO delete(Long id);

    @WebMethod
    public TeamDTO add(Long teamId, Long humanId);

    @WebMethod
    public TeamDTO deleteMember(Long teamId, Long humanId);

    @WebMethod
    public void deleteMemberByHumanId(Long humanId);
}