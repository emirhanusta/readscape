package accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import accountservice.model.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>{

}
