package soa.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordinates")
@Where(clause = "is_deleted = false")
public class CoordinatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = -636)
    @Max(value = 636)
    @Column(name = "x")
    private Long x;

    @NotNull
    @Min(value = -636)
    @Max(value = 636)
    @Column(name = "y")
    private Long y;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;
}
