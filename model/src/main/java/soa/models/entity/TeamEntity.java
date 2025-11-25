package soa.models.entity;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
import soa.models.mapper.HumansIdsMapper;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
@Where(clause = "is_deleted = false")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "humans")
    @Convert(converter = HumansIdsMapper.class)
    private List<Long> humans;

    @NotNull
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;
}