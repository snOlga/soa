package humans.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Path;
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

    public List<HumanDTO> getSome(Integer from, Integer pageSize, String filter, String sortBy)
            throws NoSuchFieldException, SecurityException {
        Sort sortQuery = getSortQuery(sortBy);
        try {
            return repo.findAll(buildQueryFilter(filter), PageRequest.of(from, pageSize, sortQuery))
                    .map(mapper::toDTO).toList();
        } catch (org.springframework.dao.InvalidDataAccessApiUsageException e) {
            return new ArrayList<>();
        }
    }

    private Sort getSortQuery(String sortLine) throws NoSuchFieldException, SecurityException {
        if (sortLine.isBlank() || sortLine.isEmpty())
            return Sort.by("id");
        String[] fieldsSorting = sortLine.split(";");
        return Sort.by(fieldsSorting);
    }

    private Specification<HumanEntity> buildQueryFilter(String filter) {
        if (filter.isBlank() || filter.isEmpty())
            return Specification.unrestricted();
        Matcher matcher = CONDITION_PATTERN.matcher(filter);
        Specification<HumanEntity> spec = Specification.not(null);
        while (matcher.find()) {
            spec = spec.and(buildSpecQueryFromOneFilter(matcher));
        }
        return spec;
    }

    private static final Pattern CONDITION_PATTERN = Pattern.compile("(\\w+.\\w+|\\w+)([<>!=]=?|==)([^;]+)");

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Specification<HumanEntity> buildSpecQueryFromOneFilter(Matcher matcher) {
        String field = matcher.group(1);
        String operator = matcher.group(2);
        String value = matcher.group(3).replaceAll("'", "").trim();
        Object typedValue = convertValue(value);
        return (root, query, builder) -> {
            Path<HumanEntity> path = root;
            String currField = field.split("\\.")[0];
            if (field.split("\\.").length > 1) {
                path = path.get(currField);
                currField = field.split("\\.")[1];
            }
            switch (operator) {
                case "=", "==":
                    return builder.equal(path.get(currField), typedValue);
                case "!=":
                    return builder.notEqual(path.get(currField), typedValue);
                case ">":
                    return builder.greaterThanOrEqualTo(path.get(currField), (Comparable) typedValue);
                case ">=":
                    return builder.greaterThanOrEqualTo(path.get(currField), (Comparable) typedValue);
                case "<":
                    return builder.lessThan(path.get(currField), (Comparable) typedValue);
                case "<=":
                    return builder.lessThanOrEqualTo(path.get(currField), (Comparable) typedValue);
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        };
    }

    private Object convertValue(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            try {
                return parseBoolean(value);
            } catch (Exception e2) {
                try {
                    return Double.valueOf(value);
                } catch (Exception e3) {
                    return value;
                }
            }
        }
    }

    private Boolean parseBoolean(String value) throws ParseException {
        switch (value.toLowerCase()) {
            case "t", "true":
                return true;
            case "f", "false":
                return false;
            default:
                throw new ParseException("Cannot parse Boolean from string: " + value);
        }
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
        HumanEntity entity = repo.findByWeaponType(type, PageRequest.of(0, 1)).toList().get(0);
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
