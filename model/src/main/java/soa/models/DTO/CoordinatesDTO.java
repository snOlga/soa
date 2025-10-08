package soa.models.DTO;

import org.hibernate.annotations.ColumnDefault;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDTO {
    
    private Long id;

    @NotNull
    @Min(value = -636)
    private Long x;

    @NotNull
    @Min(value = -636)
    private Long y;

    @NotNull
    @ColumnDefault("false")
    private Boolean isDeleted;
}
