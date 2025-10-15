package soa.models.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import soa.models.enums.Mood;
import soa.models.enums.WeaponType;
import soa.models.exception.FilterParsingException;
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
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    private CoordinatesEntity coordinates;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "is_real_hero")
    @ColumnDefault("true")
    private Boolean isRealHero = true;

    @NotBlank
    @NotNull
    @Column(name = "soundtrack")
    private String soundtrackName;

    @NotNull
    @Min(value = 0)
    @Max(value = 300)
    @Column(name = "impact_speed")
    private Float impactSpeed;

    // @Null
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type")
    private WeaponType weaponType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mood")
    private Mood mood;

    // @Null
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted = false;
}
