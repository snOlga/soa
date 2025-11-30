package ejb.service.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaBooleanDTO {
    Long id;
    Boolean result;
}
