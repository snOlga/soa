package soa.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import soa.models.entity.HumanEntity;

@Repository
public interface HumanRepository extends JpaRepository<HumanEntity, Long> {
    
}
