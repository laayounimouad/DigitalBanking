package ma.laayouni.digitalbankingapi;

import ma.laayouni.digitalbankingapi.entities.AccountOperation;
import ma.laayouni.digitalbankingapi.entities.CurrentAccount;
import ma.laayouni.digitalbankingapi.entities.Customer;
import ma.laayouni.digitalbankingapi.entities.SavingAccount;
import ma.laayouni.digitalbankingapi.entities.enums.AccountStatus;
import ma.laayouni.digitalbankingapi.entities.enums.OperationType;
import ma.laayouni.digitalbankingapi.repositories.AccountOperationRepository;
import ma.laayouni.digitalbankingapi.repositories.BankAccountRepository;
import ma.laayouni.digitalbankingapi.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                 BankAccountRepository bankAccountRepository,
                                 AccountOperationRepository accountOperationRepository){
        return (args)->{
            Stream.of("mouad", "mohamed", "zakaria", "yassine").forEach(name -> {
                Customer customer = new Customer();
                customer.setId(UUID.randomUUID().toString());
                customer.setName(name);
                customer.setEmail(name.concat("@gmail.com"));
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach( customer -> {
                CurrentAccount currentAcc = new CurrentAccount();
                currentAcc.setId(UUID.randomUUID().toString());
                currentAcc.setBalance(Math.random()*3000);
                currentAcc.setCreatedAt(new Date());
                currentAcc.setStatus(AccountStatus.values()[(int)(Math.random()*3)]);
                currentAcc.setOverDraft(3000);
                currentAcc.setCustomer(customer);

                SavingAccount savingAcc = new SavingAccount();
                savingAcc.setId(UUID.randomUUID().toString());
                savingAcc.setBalance(Math.random()*4000);
                savingAcc.setCreatedAt(new Date());
                savingAcc.setStatus(AccountStatus.values()[(int)(Math.random()*3)]);
                savingAcc.setInterestRate(Math.random()* 10);
                savingAcc.setCustomer(customer);

                bankAccountRepository.save(currentAcc);
                bankAccountRepository.save(savingAcc);
            });

            bankAccountRepository.findAll().forEach(bankAccount -> {
                for(int i=0; i<3; i++){
                    AccountOperation operation = new AccountOperation();
                    operation.setBankAccount(bankAccount);
                    operation.setType(OperationType.values()[(int)(Math.random()*2)]);
                    operation.setAmount(Math.random()*300);
                    operation.setOperationDate(new Date());
                    accountOperationRepository.save(operation);
                }
            });
        };

    }

}
