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
public class CarEntity implements Comparable<CarEntity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "coolness")
    private Long coolness;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @Override
    public boolean equals(Object obj) {
        CarEntity second = (CarEntity) obj;
        return this.name.equals(second.getName()) && this.coolness.equals(second.getCoolness());
    }

    @Override
    public int compareTo(CarEntity o) {
        return this.coolness.compareTo(o.getCoolness());
    }
}
