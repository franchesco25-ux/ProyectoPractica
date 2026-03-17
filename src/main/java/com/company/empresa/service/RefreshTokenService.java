package com.company.empresa.service;

import com.company.empresa.entity.RefreshToken;
import com.company.empresa.repository.RefreshTokenRepository;
import com.company.empresa.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;
    @Autowired
    private UserRepo userRepo;

    public RefreshToken generateRefreshToken(String username){
        RefreshToken build = RefreshToken.builder()
                .user(userRepo.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiredDate(Instant.now().plusMillis(6000000))
                .build();

        return refreshTokenRepo.save(build);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }

    public RefreshToken verifyExpirationToken(RefreshToken refreshToken){
        if(refreshToken.getExpiredDate().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException("Token no encontrado");
        }
        return refreshToken;
    }
}
