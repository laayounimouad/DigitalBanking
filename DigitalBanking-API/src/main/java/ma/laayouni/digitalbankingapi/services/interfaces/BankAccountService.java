package ma.laayouni.digitalbankingapi.services.interfaces;

import ma.laayouni.digitalbankingapi.dtos.BankAccountDto;
import ma.laayouni.digitalbankingapi.dtos.CurrentAccountDto;
import ma.laayouni.digitalbankingapi.dtos.SavingAccountDto;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service

public interface BankAccountService {
    CurrentAccountDto saveCurrentAccount(double initBalance, double overDraft, String customerId) throws CustomerNotFoundException;
    SavingAccountDto saveSavingAccount(double initBalance, double interestRate, String customerId) throws CustomerNotFoundException;
    BankAccountDto saveAccount(BankAccountDto bankAccount) throws AccountNotFoundException, CustomerNotFoundException;
    boolean deleteAccount(String accountId) throws AccountNotFoundException;
    BankAccountDto updateAccount(BankAccountDto bankAccount) throws AccountNotFoundException;

    List<BankAccountDto> getBankAccountsList(int page, int size);

    BankAccountDto getBankAccountById(String accountId) throws AccountNotFoundException;

    //AccountType getAccountType(String accountId) throws AccountNotFoundException;

    //boolean applyOperation(String accountId, double amount, OperationType operationType, String description) throws OperationFailedException, AccountNotFoundException, CustomerNotFoundException, BalanceNotSufficientException;

    //boolean debitAccount(BankAccountDTO account, double amount, String description) throws OperationFailedException, AccountNotFoundException, BalanceNotSufficientException, CustomerNotFoundException;
    //boolean creditAccount(BankAccountDTO account, double amount, String description) throws AccountNotFoundException, CustomerNotFoundException;// retrait
    //boolean transfer(String sourceAccount, String destinationAccount, double amountToTransfer) throws AccountNotFoundException, OperationFailedException, CustomerNotFoundException, BalanceNotSufficientException, TransferToTheSameAccountException;

}
