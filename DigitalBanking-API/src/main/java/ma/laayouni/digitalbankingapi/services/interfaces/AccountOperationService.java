package ma.laayouni.digitalbankingapi.services.interfaces;

import ma.laayouni.digitalbankingapi.dtos.AccountHistoryDto;
import ma.laayouni.digitalbankingapi.dtos.AccountOperationDto;
import ma.laayouni.digitalbankingapi.dtos.BankAccountDto;
import ma.laayouni.digitalbankingapi.entities.enums.OperationType;
import ma.laayouni.digitalbankingapi.exceptions.BalanceNotSufficientException;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import ma.laayouni.digitalbankingapi.exceptions.OperationFailedException;
import ma.laayouni.digitalbankingapi.exceptions.TransferToTheSameAccountException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public interface AccountOperationService {
    // save, update, delete, getOne, getList

    AccountOperationDto saveOperation(AccountOperationDto accountOperationDto) throws CustomerNotFoundException;


    AccountOperationDto updateOperation(AccountOperationDto accountOperationDto) throws OperationFailedException;

    boolean deleteOperation(long operationId) throws OperationFailedException;

    AccountOperationDto getOperationById(long id) throws OperationFailedException;

    List<AccountOperationDto> getAllOperations(int page, int size) throws AccountNotFoundException;

    List<AccountOperationDto> getAccountOperations(String accountId, int page, int size) throws AccountNotFoundException;

    AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws AccountNotFoundException;

    boolean applyOperation(String accountId, double amount, OperationType operationType, String description) throws OperationFailedException, AccountNotFoundException, CustomerNotFoundException, BalanceNotSufficientException;

    boolean debitAccount(BankAccountDto account, double amount, String description) throws BalanceNotSufficientException, AccountNotFoundException, CustomerNotFoundException;

    boolean creditAccount(BankAccountDto account, double amount, String description) throws AccountNotFoundException, CustomerNotFoundException;

    boolean transfer(String sourceAccount, String destinationAccount, double amountToTransfer) throws AccountNotFoundException, OperationFailedException, CustomerNotFoundException, BalanceNotSufficientException, TransferToTheSameAccountException;
}
