package com.tallerinyecmotor.backend.controller;

import com.tallerinyecmotor.backend.dto.DTOAuth;
import com.tallerinyecmotor.backend.utils.SHA256;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private SHA256 controlPass = new SHA256();

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody DTOAuth auth) {
        try {
            if (controlPass.verifyPassword(auth.getPassword())) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }
        } catch(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
}
