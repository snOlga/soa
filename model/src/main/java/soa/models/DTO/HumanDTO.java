package soa.models.DTO;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.constraints.*;
import lombok.*;
import soa.models.enums.Mood;
import soa.models.enums.WeaponType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HumanDTO {
    
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private CoordinatesDTO coordinates;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @NotNull
    @ColumnDefault("true")
    private Boolean isRealHero;

    @NotBlank
    @NotNull
    private String soundtrackName;

    @NotNull
    @Min(value = 0)
    @Max(value = 300)
    private Float impactSpeed;

    @Null
    private WeaponType weaponType;

    @NotNull
    private Mood mood;

    @Null
    private CarDTO car;

    private Boolean isDeleted = false;
}
