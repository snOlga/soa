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
@Table(name = "cars")
@Where(clause = "is_deleted = false")
public class CarEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "coolness", nullable = false)
    private Long coolness;

    // @NotNull
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;
}
