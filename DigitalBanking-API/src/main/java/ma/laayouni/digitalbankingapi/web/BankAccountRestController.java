package ma.laayouni.digitalbankingapi.web;

import lombok.AllArgsConstructor;
import ma.laayouni.digitalbankingapi.dtos.AccountHistoryDto;
import ma.laayouni.digitalbankingapi.dtos.AccountOperationDto;
import ma.laayouni.digitalbankingapi.dtos.BankAccountDto;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import ma.laayouni.digitalbankingapi.services.interfaces.AccountOperationService;
import ma.laayouni.digitalbankingapi.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {
    BankAccountService bankAccountService;
    AccountOperationService accountOperationService;
    // get all accounts
    @GetMapping("/accounts")
    public List<BankAccountDto> listAccounts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return bankAccountService.getBankAccountsList(page, size);
    }
    @PostMapping("/accounts")
    public BankAccountDto saveBankAccount(@RequestBody BankAccountDto bankAccountDto) throws AccountNotFoundException, CustomerNotFoundException {
        return  bankAccountService.saveAccount(bankAccountDto);
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDto getBankAccount(@PathVariable String id) throws AccountNotFoundException {
        return bankAccountService.getBankAccountById(id);
    }
    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDto> getBankAccountOperations(
            @PathVariable String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws AccountNotFoundException {
        return accountOperationService.getAccountOperations(id, page, size);
    }
    @GetMapping("/accounts/{id}/history")
    public AccountHistoryDto getBankAccountHistory(@PathVariable String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) throws AccountNotFoundException {
        return accountOperationService.getAccountHistory(id, page, size);
    }

}
