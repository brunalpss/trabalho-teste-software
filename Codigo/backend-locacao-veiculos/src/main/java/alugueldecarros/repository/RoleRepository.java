package alugueldecarros.repository;

import alugueldecarros.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);

    List<Role> findAllByNameIn(List<String> roles);
}