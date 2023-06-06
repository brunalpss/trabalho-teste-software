package alugueldecarros.repository;

import alugueldecarros.models.Role;
import alugueldecarros.models.User;
import alugueldecarros.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    List<UserRole> findAllByUser(User user);
    UserRole findByUserAndRole(User user, Role role);
    //List<Role> findAllByUserId(Long userid);
}
