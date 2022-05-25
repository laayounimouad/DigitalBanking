package ma.laayouni.digitalbankingapi.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {
    private String id;
    private String name;
    @Email
    private String email;
}
