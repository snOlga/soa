package humans.service.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import soa.models.DTO.HumanDTO;
import soa.models.entity.HumanEntity;
import soa.models.enums.WeaponType;
import soa.models.exception.FilterParsingException;
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

    public List<HumanDTO> getSome(Integer from, Integer pageSize, String filter, String sortBy)
            throws NoSuchFieldException, SecurityException {
        Sort sortQuery = getSortQuery(sortBy);
        return repo.findAll(PageRequest.of(from, (from + pageSize), sortQuery)).map(mapper::toDTO).filter(null)
                .toList();
    }

    private Sort getSortQuery(String sortLine) throws NoSuchFieldException, SecurityException {
        String[] fieldsSorting = sortLine.split(";");
        Sort sorting = Sort.by("id");
        for (String fieldStr : fieldsSorting) {
            if (HumanEntity.class.getField(fieldStr) != null)
                sorting = sorting.and(Sort.by(fieldStr));
        }
        return sorting;
    }

    private static final Pattern CONDITION_PATTERN = Pattern.compile("(\\w+)([<>!=]=?|==)([^;]+)");

    @SuppressWarnings("unchecked")
    public static Specification<HumanEntity> buildSpecQueryFromOneFilter(String filter) {
        return (root, query, builder) -> {
            Matcher matcher = CONDITION_PATTERN.matcher(filter);
            while (matcher.find()) {
                String field = matcher.group(1);
                String operator = matcher.group(2);
                String value = matcher.group(3).replaceAll("'", "").trim();

                Object typedValue = convertValue(value);

                switch (operator) {
                    case "=":
                    case "==":
                        return builder.equal(root.get(field), typedValue);
                    case "!=":
                        return builder.notEqual(root.get(field), typedValue);
                    case ">":
                        return builder.greaterThan(root.get(field), (Comparable) typedValue);
                    case ">=":
                        return builder.greaterThanOrEqualTo(root.get(field), (Comparable) typedValue);
                    case "<":
                        return builder.lessThan(root.get(field), (Comparable) typedValue);
                    case "<=":
                        return builder.lessThanOrEqualTo(root.get(field), (Comparable) typedValue);
                    default:
                        throw new IllegalArgumentException("Unsupported operator: " + operator);
                }
            }
            return null;
        };
    }

    private static Object convertValue(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            try {
                return Boolean.valueOf(value);
            } catch (Exception e2) {
                try {
                    Double.valueOf(value);
                } catch (Exception e3) {
                    return value;
                }
            }
        }
        return value;
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
