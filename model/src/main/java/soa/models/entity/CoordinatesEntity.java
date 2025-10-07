package soa.models.entity;

import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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
    @Column(name = "x", nullable = false)
    private Long x;

    @NotNull
    @Min(value = -636)
    @Column(name = "y", nullable = false)
    private Long y;
}
