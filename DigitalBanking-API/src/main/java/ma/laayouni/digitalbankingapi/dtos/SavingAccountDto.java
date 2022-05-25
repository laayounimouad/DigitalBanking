package ma.laayouni.digitalbankingapi.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class SavingAccountDto extends BankAccountDto implements Serializable {
    private double interestRate;
}
