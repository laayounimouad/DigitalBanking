package ma.laayouni.digitalbankingapi.web;

import lombok.AllArgsConstructor;
import ma.laayouni.digitalbankingapi.dtos.BankAccountDto;
import ma.laayouni.digitalbankingapi.services.interfaces.AccountOperationService;
import ma.laayouni.digitalbankingapi.services.interfaces.BankAccountService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
