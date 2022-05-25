package ma.laayouni.digitalbankingapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer")// the same name used in BankAccount
    // mappedBy assist the bidirectional association
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccountList;

}

