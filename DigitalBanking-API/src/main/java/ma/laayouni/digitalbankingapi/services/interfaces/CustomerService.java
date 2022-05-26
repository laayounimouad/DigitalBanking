package ma.laayouni.digitalbankingapi.services.interfaces;

import ma.laayouni.digitalbankingapi.dtos.CustomerDto;
import ma.laayouni.digitalbankingapi.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    // save, update, delete, getOne, getList
    CustomerDto saveCustomer(CustomerDto customerDto) throws CustomerNotFoundException;
    CustomerDto updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException;
    boolean deleteCustomer(String customerId) throws CustomerNotFoundException;

    CustomerDto getCustomerById(String id) throws CustomerNotFoundException;
    List<CustomerDto> getCustomersList(int page, int size, String keyword);

}

