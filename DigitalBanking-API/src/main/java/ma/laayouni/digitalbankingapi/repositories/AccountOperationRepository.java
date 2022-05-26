package ma.laayouni.digitalbankingapi.repositories;

import ma.laayouni.digitalbankingapi.entities.AccountOperation;
import ma.laayouni.digitalbankingapi.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    Page<AccountOperation> getAccountOperationsByBankAccount(BankAccount bankAccount, Pageable pageable);
}