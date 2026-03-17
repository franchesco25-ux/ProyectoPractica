package com.company.empresa.controller;

import com.company.empresa.dto.*;
import com.company.empresa.entity.RefreshToken;
import com.company.empresa.entity.User;
import com.company.empresa.service.AdminService;
import com.company.empresa.service.RefreshTokenService;
import com.company.empresa.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Autentication de Usuarios", description = "Controlador de Acceso para Usuarios")
public class UserController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    @Operation(
            summary = "Login de Usuarios",
            description = "Registra un usuario y devuelve los valores obtenidos",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitar para registro de datos",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = RegisterDTO.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticacion Satisfactoria",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseRegisterDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ResponseRegisterDTO> registerUser(@RequestBody RegisterDTO registroDTO) throws Exception {
        return ResponseEntity.ok().body(adminService.register(registroDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> loginUser(@RequestBody LoginDTO loginDTO){
         return ResponseEntity.ok().body(adminService.login(loginDTO));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<ResponseRegisterDTO> registerAdmin(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.registerAdmin(registerDTO));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.refreshToken(request));
    }
}
