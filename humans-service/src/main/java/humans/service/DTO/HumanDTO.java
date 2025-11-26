package humans.service.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

import humans.service.enums.Mood;
import humans.service.enums.WeaponType;
import lombok.*;

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

    private LocalDateTime creationDate;

    @NotNull
    private Boolean isRealHero;

    @NotBlank
    @NotNull
    private String soundtrackName;

    @NotNull
    @Min(value = 0)
    @Max(value = 300)
    private Float impactSpeed;

    private WeaponType weaponType;

    @NotNull
    private Mood mood;

    private CarDTO car;

    private Boolean isDeleted;
}
