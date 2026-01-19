package ejb.service.DTO;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "team", namespace = "http://i.ejb.service.ejb/")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDTO implements Serializable {

    @XmlElement(name = "id", namespace = "http://i.ejb.service.ejb/")
    private Long id;

    @NotBlank
    @NotNull
    @XmlElement(name = "name", namespace = "http://i.ejb.service.ejb/")
    private String name;

    @NotNull
    @XmlElementWrapper(name = "humans", namespace = "http://i.ejb.service.ejb/")
    @XmlElement(name = "item", namespace = "http://i.ejb.service.ejb/")
    private List<Long> humans;

    // @XmlElement(name = "isDeleted")
    private Boolean isDeleted = false;
}