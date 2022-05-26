package ma.laayouni.digitalbankingapi.web;


import lombok.AllArgsConstructor;
import ma.laayouni.digitalbankingapi.dtos.AccountOperationDto;
import ma.laayouni.digitalbankingapi.services.interfaces.AccountOperationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AccountOperationRestController {
    AccountOperationService accountOperationService;

    @GetMapping("/operations")
    public List<AccountOperationDto> getOperationsList(){
        return  null;
    }
}
