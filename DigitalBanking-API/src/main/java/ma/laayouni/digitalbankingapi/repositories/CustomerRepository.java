package ma.laayouni.digitalbankingapi.repositories;

import ma.laayouni.digitalbankingapi.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> findCustomersByNameContains(String keyword, Pageable pageable);
}