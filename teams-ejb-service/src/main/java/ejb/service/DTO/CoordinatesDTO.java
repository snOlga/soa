package ejb.service.DTO;

import javax.validation.constraints.*;
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

    private Boolean isDeleted;
}
