package soa.models.entity;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
@Where(clause = "is_deleted = false")
@TypeDef(
    name = "json",
    typeClass = JsonBinaryType.class
)
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Type(type = "json")
    @Column(name = "humans")
    private List<Long> humans;

    @NotNull
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;
}