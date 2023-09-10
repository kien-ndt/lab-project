package demo.lab.db.repository;

import demo.lab.db.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<RoleEntity, Integer> {

    List<RoleEntity> findByAccountId(Integer accountId);

}
