package alugueldecarros.service;

import alugueldecarros.models.Role;
import alugueldecarros.models.User;
import alugueldecarros.models.UserRole;

import java.util.List;

public interface UserRoleService {


    List<Role> findAllByUserId(Long id);
    List<UserRole> findAllByUser(User id);
}
