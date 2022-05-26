package ma.laayouni.digitalbankingapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.laayouni.digitalbankingapi.dtos.BankAccountDto;
import ma.laayouni.digitalbankingapi.dtos.CurrentAccountDto;
import ma.laayouni.digitalbankingapi.dtos.SavingAccountDto;
import ma.laayouni.digitalbankingapi.entities.BankAccount;
import ma.laayouni.digitalbankingapi.entities.CurrentAccount;
import ma.laayouni.digitalbankingapi.entities.Customer;
import ma.laayouni.digitalbankingapi.entities.SavingAccount;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import ma.laayouni.digitalbankingapi.mappers.BankAccountMapperImpl;
import ma.laayouni.digitalbankingapi.repositories.AccountOperationRepository;
import ma.laayouni.digitalbankingapi.repositories.BankAccountRepository;
import ma.laayouni.digitalbankingapi.repositories.CustomerRepository;
import ma.laayouni.digitalbankingapi.services.interfaces.BankAccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImp implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    //private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl mapper;

    @Override
    public CurrentAccountDto saveCurrentAccount(double initBalance, double overDraft, String customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return mapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingAccountDto saveSavingAccount(double initBalance, double interestRate, String customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return mapper.fromSavingBankAccount(savedBankAccount);
    }

    @Override
    public BankAccountDto saveAccount(BankAccountDto bankAccount) throws AccountNotFoundException, CustomerNotFoundException {
        log.info("Saving account ....");
        if(bankAccount == null) throw new AccountNotFoundException("Invalid account [NULL]");
        if (bankAccount instanceof SavingAccountDto){
            SavingAccountDto savingAccountDto =  (SavingAccountDto) bankAccount;
            return saveSavingAccount(savingAccountDto.getBalance(), savingAccountDto.getInterestRate(), savingAccountDto.getCustomerDto().getId());
        }
        else {
            CurrentAccountDto currentAccountDto = (CurrentAccountDto) bankAccount;
            return saveCurrentAccount(currentAccountDto.getBalance(), currentAccountDto.getOverDraft(), currentAccountDto.getCustomerDto().getId());
        }
    }

    @Override
    public boolean deleteAccount(String accountId) throws AccountNotFoundException {
        log.info("Deleting account ....");
        BankAccountDto accountDTO =  getBankAccountById(accountId);
        bankAccountRepository.delete(mapper.bankAccountFromDTO(accountDTO));
        return true;
    }

    @Override
    public BankAccountDto updateAccount(BankAccountDto bankAccount) throws AccountNotFoundException {
        log.info("Updating account ....");
        getBankAccountById(bankAccount.getId());
        return mapper.dtoFromBankAccount(bankAccountRepository.save(mapper.bankAccountFromDTO(bankAccount)));
    }

    @Override
    public List<BankAccountDto> getBankAccountsList(int page, int size) {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll(PageRequest.of(page, size)).getContent();
        return  bankAccounts.stream().map(bankAccount ->mapper.dtoFromBankAccount(bankAccount)).collect(Collectors.toList());
    }

    @Override
    public BankAccountDto getBankAccountById(String accountId) throws AccountNotFoundException {
        return mapper.dtoFromBankAccount(bankAccountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(null)));
    }
}
