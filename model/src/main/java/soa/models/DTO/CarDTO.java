package soa.models.DTO;
import org.hibernate.annotations.ColumnDefault;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @Min(value = 0)
    private Long coolness;

    
    @NotNull
    @ColumnDefault("false")
    private Boolean isDeleted;
}
