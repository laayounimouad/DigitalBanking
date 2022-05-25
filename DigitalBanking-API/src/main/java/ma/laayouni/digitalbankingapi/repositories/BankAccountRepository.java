package ma.laayouni.digitalbankingapi.repositories;

import ma.laayouni.digitalbankingapi.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}