package ejb.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ejb.service.entity.TeamEntity;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    @Query(value = "SELECT * FROM teams t WHERE t.humans ~ CONCAT('(^|,)', :humanId, '(,|$)')", nativeQuery = true)
    List<TeamEntity> findTeamsContainingHuman(@Param("humanId") Long humanId);
}
