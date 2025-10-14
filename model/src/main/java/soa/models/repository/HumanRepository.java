package soa.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import soa.models.entity.HumanEntity;
import soa.models.enums.WeaponType;


@Repository
public interface HumanRepository extends JpaRepository<HumanEntity, Long> {

    @Query("update HumanEntity h set h.isDeleted = true where h.weaponType = ?1")
    public void deleteAllByWeaponType(WeaponType weaponType);

    public HumanEntity findByWeaponType(WeaponType weaponType);

    @Query("update HumanEntity h set h.isDeleted = true where h.car.coolness < ?1")
    public void deleteAllByLessCoolCar(Integer lessCoolness);
}
