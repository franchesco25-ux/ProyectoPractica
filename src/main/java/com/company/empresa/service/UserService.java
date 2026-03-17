package com.company.empresa.service;

import com.company.empresa.entity.User;
import com.company.empresa.model.UserPrincipal;
import com.company.empresa.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepo.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Usuario no Encontrado");
        }
        return new UserPrincipal(user);
    }
}
