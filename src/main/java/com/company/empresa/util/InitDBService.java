package com.company.empresa.util;

import com.company.empresa.entity.ERole;
import com.company.empresa.entity.Permission;
import com.company.empresa.entity.Role;
import com.company.empresa.entity.User;
import com.company.empresa.repository.PermissionRepository;
import com.company.empresa.repository.RoleRepository;
import com.company.empresa.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
@AllArgsConstructor
public class InitDBService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostConstruct
    public void init() {
        if (roleRepository.findByName(ERole.ADMIN) != null) return;

        Permission readUser = permissionRepository.save(new Permission( "READ_USER"));
        Permission deletePost = permissionRepository.save(new Permission("DELETE_POST"));

        Role admin = new Role();
        admin.setName(ERole.ADMIN);
        admin.setPermissions(Set.of(readUser, deletePost));
        roleRepository.save(admin);

        Role moderator = new Role();
        moderator.setName(ERole.MODERATOR);
        moderator.setPermissions(Set.of(deletePost));
        roleRepository.save(moderator);

        Role user = new Role();
        user.setName(ERole.USER);
        roleRepository.save(user);
    }

}
