package ma.laayouni.digitalbankingapi.mappers;


import ma.laayouni.digitalbankingapi.dtos.AccountOperationDto;
import ma.laayouni.digitalbankingapi.dtos.CurrentAccountDto;
import ma.laayouni.digitalbankingapi.dtos.CustomerDto;
import ma.laayouni.digitalbankingapi.dtos.SavingAccountDto;
import ma.laayouni.digitalbankingapi.entities.AccountOperation;
import ma.laayouni.digitalbankingapi.entities.CurrentAccount;
import ma.laayouni.digitalbankingapi.entities.Customer;
import ma.laayouni.digitalbankingapi.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service
public class BankAccountMapperImpl {
    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDTO=new CustomerDto();
        BeanUtils.copyProperties(customer,customerDTO);
        return  customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDto customerDto){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public SavingAccountDto fromSavingBankAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto=new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDto;
    }

    public SavingAccount fromSavingBankAccountDTO(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDto.getCustomerDto()));
        return savingAccount;
    }

    public CurrentAccountDto fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentAccountDto bankAccountDTO=new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount,bankAccountDTO);
        bankAccountDTO.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        bankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return bankAccountDTO;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDto.getCustomerDto()));
        return currentAccount;
    }

    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDto accountOperationDto=new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        return accountOperationDto;
    }
}
