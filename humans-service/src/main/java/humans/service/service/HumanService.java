package humans.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import soa.models.DTO.HumanDTO;
import soa.models.entity.HumanEntity;
import soa.models.enums.WeaponType;
import soa.models.exception.HumanNotFoundException;
import soa.models.mapper.HumanMapper;
import soa.models.repository.HumanRepository;

@Service
public class HumanService {

    @Autowired
    HumanRepository repo;
    @Autowired
    HumanMapper mapper;

    public HumanDTO save(HumanDTO dto) {
        HumanEntity entity = mapper.toEntity(dto);
        repo.save(entity);
        return mapper.toDTO(entity);
    }

    public HumanDTO get(Long id) {
        return mapper.toDTO(repo.findById(id).orElse(null));
    }

    public List<HumanDTO> getSome(Integer from, Integer pageSize, String filter, String sortBy) {
        return repo.findAll(PageRequest.of(from, (from + pageSize))).map(mapper::toDTO).toList();
    }

    public HumanDTO update(Long id, HumanDTO dto) {
        boolean isPresent = repo.findById(id).isPresent();
        if (!isPresent)
            throw new HumanNotFoundException();
        HumanEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        repo.save(updated);
        return mapper.toDTO(updated);
    }

    public HumanDTO delete(Long id) {
        HumanEntity entity = repo.findById(id).orElse(null);
        if (entity == null)
            throw new HumanNotFoundException();
        entity.setIsDeleted(true);
        repo.save(entity);
        return mapper.toDTO(entity);
    }

    public HumanDTO deleteByWeaponType(WeaponType type) {
        HumanEntity entity = repo.findByWeaponType(type);
        if (entity == null)
            return null;
        entity.setIsDeleted(true);
        repo.save(entity);
        return mapper.toDTO(entity);
    }

    public void deleteAllByWeaponType(WeaponType type) {
        repo.deleteAllByWeaponType(type);
    }

    public void deleteAllByLessCoolCar(Integer coolness) {
        repo.deleteAllByLessCoolCar(coolness);
    }
}
