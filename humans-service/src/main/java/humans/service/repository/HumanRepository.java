package humans.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import humans.service.entity.HumanEntity;
import humans.service.enums.WeaponType;

@Repository
public interface HumanRepository extends JpaRepository<HumanEntity, Long>, JpaSpecificationExecutor<HumanEntity> {

    @Transactional
    @Modifying 
    @Query("update HumanEntity h set h.isDeleted = true where h.weaponType = ?1")
    public void deleteAllByWeaponType(WeaponType weaponType);

    public Page<HumanEntity> findByWeaponType(WeaponType weaponType, Pageable pageable);

    @Transactional
    @Modifying 
    @Query("update HumanEntity h set h.isDeleted = true where h.car.coolness < ?1")
    public void deleteAllByLessCoolCar(Integer lessCoolness);
}
