package ma.laayouni.digitalbankingapi.repositories;

import ma.laayouni.digitalbankingapi.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}