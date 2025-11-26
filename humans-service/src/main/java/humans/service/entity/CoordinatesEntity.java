package humans.service.entity;

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
public class CoordinatesEntity implements Comparable<CoordinatesEntity> {

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

    @Override
    public boolean equals(Object obj) {
        CoordinatesEntity second = (CoordinatesEntity) obj;
        return this.x.equals(second.getX()) && this.y.equals(second.getY());
    }

    @Override
    public int compareTo(CoordinatesEntity o) {
        return this.x.compareTo(o.getX());
    }

}
