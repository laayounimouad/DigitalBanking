package ma.laayouni.digitalbankingapi.dtos;

import lombok.Data;
import ma.laayouni.digitalbankingapi.entities.enums.OperationType;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountOperationDto implements Serializable {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    //private BankAccountDto bankAccount;
}
