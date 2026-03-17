package com.company.empresa.service;

import com.company.empresa.dto.*;
import com.company.empresa.entity.ERole;
import com.company.empresa.entity.RefreshToken;
import com.company.empresa.entity.Role;
import com.company.empresa.entity.User;
import com.company.empresa.exceptions.ErrorResponse;
import com.company.empresa.exceptions.UserNameNotFoundException;
import com.company.empresa.mapper.Mapper;
import com.company.empresa.model.UserPrincipal;
import com.company.empresa.repository.RefreshTokenRepository;
import com.company.empresa.repository.RoleRepository;
import com.company.empresa.repository.UserRepo;
import com.company.empresa.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticate;

    @Autowired
    private RefreshTokenService refreshToken;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public ResponseRegisterDTO register(RegisterDTO registerDTO) throws Exception {
        userExists(registerDTO.getUsername());
        User user = Mapper.toUser(registerDTO, passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(setRol("USER"));
        userRepo.save(user);
        return new ResponseRegisterDTO(registerDTO.getUsername());
    }


    @Transactional
    public ResponseRegisterDTO registerAdmin(RegisterDTO registerDTO){
        User user = Mapper.toUser(registerDTO, passwordEncoder.encode(registerDTO.getPassword()));
        user.setRoles(setRol("ADMIN"));
        userRepo.save(user);
        return new ResponseRegisterDTO(registerDTO.getUsername());
    }

    public ResponseLoginDTO login(LoginDTO loginDTO){
            Authentication authentication = authenticate.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                UserPrincipal author = (UserPrincipal) userService.loadUserByUsername(loginDTO.getUsername());
                var jwt = jwtUtil.generateToken(author);
                var refresh = refreshToken.generateRefreshToken(author.getUsername());
                return new ResponseLoginDTO(author.getUsername(), jwt, refresh.getToken());
            }
            return new ResponseLoginDTO(null, null, null);
    }

    public ResponseLoginDTO refreshToken(RefreshTokenRequestDTO request){
        return refreshTokenService.findByToken(request.getToken())
                .map(refreshTokenService::verifyExpirationToken)
                .map(tokenDB -> {
                    UserPrincipal user = (UserPrincipal) userService
                            .loadUserByUsername(tokenDB.getUser().getUsername());

                    var jwt = jwtUtil.generateToken(user);

                    return new ResponseLoginDTO(
                            user.getUsername(),
                            jwt,
                            tokenDB.getToken()
                    );
                })
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
    }


    private void userExists(String username) throws UserNameNotFoundException {
        if(userRepo.existsByUsername(username)){
            throw new UserNameNotFoundException("Nombre de Usuario ya existente", new ErrorResponse("Ha ocurrido un error inesperado", "error"));
        }
    }


    private Set<Role> setRol(String name){
        Role role = roleRepository.findByName(ERole.valueOf(name));
        if(role.getName().equals(ERole.ADMIN)){
            return Set.of(role);
        }
        Role role1 = roleRepository.findByName(ERole.USER);
        return Set.of(role1);
    }
}
