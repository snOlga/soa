package ejb.service.repository;

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
}
