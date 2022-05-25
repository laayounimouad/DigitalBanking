package ma.laayouni.digitalbankingapi.dtos;

import lombok.Data;
import ma.laayouni.digitalbankingapi.entities.enums.AccountStatus;

import java.io.Serializable;
import java.util.Date;

@Data
public class BankAccountDto implements Serializable {
    private String id;
    private Date createdAt;
    private double balance;
    private AccountStatus status;
    private String currency;
    private String type;
    private CustomerDto customerDto;
}
