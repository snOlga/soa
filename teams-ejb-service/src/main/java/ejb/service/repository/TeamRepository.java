package ejb.service.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ejb.service.entity.TeamEntity;

@Stateless
public class TeamRepository {

    @PersistenceContext(unitName = "TeamsPU")
    private EntityManager em;

    public TeamEntity findById(Long id) {
        return em.find(TeamEntity.class, id);
    }

    public TeamEntity save(TeamEntity entity) {
        return em.merge(entity);
    }

    public boolean existsById(Long id) {
        return em.find(TeamEntity.class, id) != null;
    }

    public void deleteMember(Long humanId) {
        List<TeamEntity> teams = em.createNativeQuery(
                "SELECT * FROM teams t " +
                        "WHERE t.humans REGEXP '(^|,)' || ? || '(,|$)'",
                TeamEntity.class)
                .setParameter(1, humanId.toString())
                .getResultList();

        for (TeamEntity t : teams) {
            t.getHumans().remove(humanId);
            em.merge(t);
        }
    }

}
