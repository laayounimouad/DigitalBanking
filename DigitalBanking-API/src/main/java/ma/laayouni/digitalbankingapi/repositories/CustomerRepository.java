package ma.laayouni.digitalbankingapi.repositories;

import ma.laayouni.digitalbankingapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}