package ma.laayouni.digitalbankingapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.laayouni.digitalbankingapi.dtos.AccountHistoryDto;
import ma.laayouni.digitalbankingapi.dtos.AccountOperationDto;
import ma.laayouni.digitalbankingapi.dtos.BankAccountDto;
import ma.laayouni.digitalbankingapi.entities.AccountOperation;
import ma.laayouni.digitalbankingapi.entities.enums.OperationType;
import ma.laayouni.digitalbankingapi.exceptions.BalanceNotSufficientException;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import ma.laayouni.digitalbankingapi.exceptions.OperationFailedException;
import ma.laayouni.digitalbankingapi.exceptions.TransferToTheSameAccountException;
import ma.laayouni.digitalbankingapi.mappers.BankAccountMapperImpl;
import ma.laayouni.digitalbankingapi.repositories.AccountOperationRepository;
import ma.laayouni.digitalbankingapi.services.interfaces.AccountOperationService;
import ma.laayouni.digitalbankingapi.services.interfaces.BankAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AccountOperationServiceImp implements AccountOperationService {
    AccountOperationRepository operationRepository;
    BankAccountService bankAccountService;
    BankAccountMapperImpl mapper;
    @Override
    public AccountOperationDto saveOperation(AccountOperationDto accountOperation) throws CustomerNotFoundException {
        log.info("Saving operation ....");
        if(accountOperation == null) throw new CustomerNotFoundException("Invalid operation [NULL]");
        return mapper.fromAccountOperation(operationRepository.save(mapper.accountOperationFromDTO(accountOperation)));
    }

    @Override
    public AccountOperationDto updateOperation(AccountOperationDto accountOperation) throws OperationFailedException {
        log.info("Updating operation ....");
        getOperationById(accountOperation.getId());
        return mapper.fromAccountOperation(operationRepository.save(mapper.accountOperationFromDTO(accountOperation)));
    }

    @Override
    public boolean deleteOperation(long operationId) throws OperationFailedException {
        log.info("Deleting operation ....");
        AccountOperationDto operationDto =  getOperationById(operationId);
        operationRepository.delete(mapper.accountOperationFromDTO(operationDto));
        return true;
    }

    @Override
    public AccountOperationDto getOperationById(long id) throws OperationFailedException {
        log.info("Selecting an operation ....");
        return mapper.fromAccountOperation(operationRepository.findById(id).orElseThrow(() -> new OperationFailedException(null)));

    }

    @Override
    public List<AccountOperationDto> getAccountOperations(String accountId, int page, int size) throws AccountNotFoundException {
        BankAccountDto bankAccountDto = bankAccountService.getBankAccountById(accountId);
        List<AccountOperation> accountOperationList = operationRepository.getAccountOperationsByBankAccount(mapper.bankAccountFromDTO(bankAccountDto), PageRequest.of(page, size)).getContent();
        return accountOperationList.stream().map((accountOperation -> mapper.fromAccountOperation(accountOperation))).collect(Collectors.toList());
    }

    @Override
    public List<AccountOperationDto> getAllOperations(int page, int size) throws AccountNotFoundException {
        List<AccountOperation> accountOperationList = operationRepository.findAll(PageRequest.of(page, size)).getContent();
        return accountOperationList.stream().map((accountOperation -> mapper.fromAccountOperation(accountOperation))).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws AccountNotFoundException {
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        BankAccountDto bankAccountDto = bankAccountService.getBankAccountById(accountId);
        Page<AccountOperation> accountOperationPage = operationRepository.getAccountOperationsByBankAccount(mapper.bankAccountFromDTO(bankAccountDto), PageRequest.of(page, size));
        List<AccountOperationDto>  accountOperationDTOList= accountOperationPage.getContent().stream().map((accountOperation -> mapper.fromAccountOperation(accountOperation))).collect(Collectors.toList());

        accountHistoryDto.setAccountId(bankAccountDto.getId());
        accountHistoryDto.setBalance(bankAccountDto.getBalance());
        accountHistoryDto.setTotalPages(accountOperationPage.getTotalPages());
        accountHistoryDto.setPageSize(accountOperationPage.getSize());
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setAccountOperationDTOList(accountOperationDTOList);

        return  accountHistoryDto;
    }



    @Override
    public boolean applyOperation(String accountId, double amount, OperationType operationType, String description) throws OperationFailedException, AccountNotFoundException, CustomerNotFoundException, BalanceNotSufficientException {
        log.info("Applying an operation....");
        BankAccountDto account = bankAccountService.getBankAccountById(accountId);

        if(amount <= 0) throw  new OperationFailedException("Operation Failed, amount <= 0 !");
        // pass operation
        if(operationType.equals(OperationType.CREDIT)){
            creditAccount(account, amount, description);
        }
        else if (operationType.equals(OperationType.DEBIT)) {
            debitAccount(account, amount, description);
        }
        return true;
    }



    @Override
    public boolean debitAccount(BankAccountDto account, double amount, String description) throws BalanceNotSufficientException, AccountNotFoundException, CustomerNotFoundException {
        log.info("Applying a debit....");
        double balance = account.getBalance();
        if(balance < amount) throw new BalanceNotSufficientException("Operation Failed, balance < amount !");

        // create the operation
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        accountOperationDto.setType(OperationType.DEBIT);
        //accountOperationDto.setBankAccountDTO(account);
        accountOperationDto.setOperationDate(new Date());
        accountOperationDto.setAmount(amount);
        accountOperationDto.setDescription(description);

        // save operation
        saveOperation(mapper.fromAccountOperation(mapper.accountOperationFromDTO(accountOperationDto)));
        // update account data

        account.setBalance(balance - amount);
        bankAccountService.updateAccount(account);

        return true;
    }

    @Override
    public boolean creditAccount(BankAccountDto account, double amount, String description) throws AccountNotFoundException, CustomerNotFoundException {
        log.info("Applying a credit....");

        double balance = account.getBalance();
        // create the operation
        AccountOperationDto operationDto = new AccountOperationDto();
        operationDto.setType(OperationType.CREDIT);
        //operationDto.setBankAccountDTO(account);
        operationDto.setOperationDate(new Date());
        operationDto.setAmount(amount);
        operationDto.setDescription(description);
        // save operation
        saveOperation(operationDto);
        // update account data

        account.setBalance(balance + amount);
        //account.getAccountOperationList().add(accountOperation);
        bankAccountService.updateAccount(account);


        return true;
    }

    @Override
    public boolean transfer(String sourceAccount, String destinationAccount, double amountToTransfer) throws AccountNotFoundException, OperationFailedException, CustomerNotFoundException, BalanceNotSufficientException, TransferToTheSameAccountException {
        log.info("Applying a transfer....");

        final  String description = "Transfer operation from ".concat(sourceAccount).concat(" To ").concat("destination");
        if(sourceAccount.equals(destinationAccount)) throw new TransferToTheSameAccountException("Operation failed, trying to transfer from and to the same account !");
        // Making sure that the two are there (Before any operation)
        bankAccountService.getBankAccountById(sourceAccount);
        bankAccountService.getBankAccountById(destinationAccount);

        applyOperation(sourceAccount, amountToTransfer, OperationType.DEBIT, description);
        applyOperation(destinationAccount, amountToTransfer, OperationType.CREDIT, description);

        return false;
    }

}
