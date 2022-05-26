package ma.laayouni.digitalbankingapi.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.laayouni.digitalbankingapi.dtos.CustomerDto;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import ma.laayouni.digitalbankingapi.services.interfaces.BankAccountService;
import ma.laayouni.digitalbankingapi.services.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    private CustomerService customerService;


    @GetMapping("/customers")
    public List<CustomerDto> customers(@RequestParam(name = "page",defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "5") int size,
                                       @RequestParam(name = "keyword", defaultValue = "") String keyword){
        return customerService.getCustomersList(page, size, keyword);
    }
    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable(name = "id") String id) throws CustomerNotFoundException {
        return customerService.getCustomerById(id);
    }
    @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
        return customerService.saveCustomer(customerDto);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDto updateCustomer(@PathVariable String id, @RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
        customerDto.setId(id);
        // TODO: correct error return the same customer without editings
        return customerService.saveCustomer(customerDto);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);
    }
}
