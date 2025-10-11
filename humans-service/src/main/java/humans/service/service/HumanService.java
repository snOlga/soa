package humans.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.inject.Inject;
import soa.models.DTO.HumanDTO;
import soa.models.entity.HumanEntity;
import soa.models.mapper.HumanMapper;
import soa.models.repository.HumanRepository;

@Service
public class HumanService {

    @Inject
    HumanRepository humanRepository;
    @Autowired
    HumanMapper humanMapper;

    public HumanDTO save(HumanDTO dto) {
        HumanEntity entity = humanMapper.toEntity(dto);
        humanRepository.save(entity);
        return humanMapper.toDTO(entity);
    }

    public HumanDTO get(Long id) {
        return humanMapper.toDTO(humanRepository.findById(id).orElse(null));
    }
}
