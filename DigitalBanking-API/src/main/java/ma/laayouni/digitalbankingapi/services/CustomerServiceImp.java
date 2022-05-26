package ma.laayouni.digitalbankingapi.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.laayouni.digitalbankingapi.dtos.CustomerDto;
import ma.laayouni.digitalbankingapi.entities.Customer;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;
import ma.laayouni.digitalbankingapi.mappers.BankAccountMapperImpl;
import ma.laayouni.digitalbankingapi.repositories.AccountOperationRepository;
import ma.laayouni.digitalbankingapi.repositories.BankAccountRepository;
import ma.laayouni.digitalbankingapi.repositories.CustomerRepository;
import ma.laayouni.digitalbankingapi.services.interfaces.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImp implements CustomerService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl mapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) throws CustomerNotFoundException {
        log.info("Saving new Customer");
        Customer customer=mapper.fromCustomerDTO(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException {
        log.info("Saving new Customer");
        Customer customer=mapper.fromCustomerDTO(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.fromCustomer(savedCustomer);
    }

    @Override
    public boolean deleteCustomer(String customerId) throws CustomerNotFoundException {
        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public CustomerDto getCustomerById(String id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        return mapper.fromCustomer(customer);
    }

    @Override
    public List<CustomerDto> getCustomersList(int page, int size, String keyword) {
        log.info("Selecting  customers ....");
        List<Customer> customersList;
        // mapping
        if(keyword != null) customersList =  customerRepository.findCustomersByNameContains(keyword, PageRequest.of(page, size)).getContent();
        else customersList =  customerRepository.findAll( PageRequest.of(page, size)).getContent();

        return customersList.stream().map(customer -> {
            return mapper.fromCustomer(customer);
        }).collect(Collectors.toList());
    }
}
