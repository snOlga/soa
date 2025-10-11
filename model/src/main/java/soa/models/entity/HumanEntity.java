package soa.models.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import soa.models.enums.Mood;
import soa.models.enums.WeaponType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "humans")
@Where(clause = "is_deleted = false")
public class HumanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "coordinates_id", nullable = false)
    private CoordinatesEntity coordinates;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "is_real_hero")
    @ColumnDefault("true")
    private Boolean isRealHero;

    @NotBlank
    @NotNull
    @Column(name = "soundtrack", nullable = false)
    private String soundtrackName;

    @NotNull
    @Min(value = 0)
    @Max(value = 300)
    @Column(name = "impact_speed", nullable = false)
    private Float impactSpeed;

    @Null
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type")
    private WeaponType weaponType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mood")
    private Mood mood;

    @Null
    @OneToOne
    @JoinColumn(name = "car_id", nullable = true)
    private CarEntity car;

    @NotNull
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted;
}
