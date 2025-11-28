package ejb.service.DTO;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
