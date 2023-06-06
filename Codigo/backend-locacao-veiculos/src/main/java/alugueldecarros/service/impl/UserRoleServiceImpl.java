package alugueldecarros.service.impl;

import alugueldecarros.models.Role;
import alugueldecarros.models.User;
import alugueldecarros.models.UserRole;
import alugueldecarros.repository.UserRoleRepository;
import alugueldecarros.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    public List<Role> findAllByUserId(Long id) {
        return null;// this.roleRepository.findAllByUserId(id);
    }

    @Override
    public List<UserRole> findAllByUser(User user) {
        return this.roleRepository.findAllByUser(user);
    }


}
