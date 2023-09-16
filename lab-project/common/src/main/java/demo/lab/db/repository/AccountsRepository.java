package demo.lab.db.repository;

import demo.lab.db.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<AccountEntity, Integer> {

    Optional<AccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
