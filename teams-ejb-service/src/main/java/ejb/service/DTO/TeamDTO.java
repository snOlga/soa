package ejb.service.DTO;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO implements Serializable {
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private List<Long> humans;

    private Boolean isDeleted;
}
