package ejb.service.DTO;

import java.io.Serializable;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO implements Serializable {
    
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @Min(value = 0)
    private Long coolness;

    private Boolean isDeleted;
}
