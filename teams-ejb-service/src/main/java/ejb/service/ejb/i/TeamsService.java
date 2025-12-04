package ejb.service.ejb.i;

import javax.ejb.Remote;

import ejb.service.DTO.TeamDTO;

@Remote
public interface TeamsService {
    public TeamDTO get(Long id);

    public TeamDTO create(TeamDTO dto);

    public TeamDTO delete(Long id);

    public TeamDTO add(Long teamId, Long humanId);

    public TeamDTO deleteMember(Long teamId, Long humanId);

    public void deleteMember(Long humanId);
}