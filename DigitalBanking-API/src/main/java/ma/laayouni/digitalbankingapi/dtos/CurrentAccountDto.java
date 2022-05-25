package ma.laayouni.digitalbankingapi.dtos;

import lombok.Data;
import ma.laayouni.digitalbankingapi.entities.Customer;
import ma.laayouni.digitalbankingapi.entities.enums.AccountStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CurrentAccountDto extends BankAccountDto implements Serializable  {
    private double overDraft;
}
