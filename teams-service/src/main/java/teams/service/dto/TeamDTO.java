package teams.service.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private List<Long> humans;

    private Boolean isDeleted;
}
