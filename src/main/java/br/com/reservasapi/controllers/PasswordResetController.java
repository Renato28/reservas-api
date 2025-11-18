package br.com.reservasapi.controllers;

import br.com.reservasapi.dto.ForgotPasswordRequest;
import br.com.reservasapi.dto.ResetPasswordRequest;
import br.com.reservasapi.services.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    @Operation(summary = "Envia e-mail para recuperação de senha", description = "Retorna mensagem de e-mail enviado")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        passwordResetService.solicitarRecuperacao(request.getEmail());
        return ResponseEntity.ok("E-mail enviado para recuperação de senha.");
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Redefinição de senha", description = "Retorna mensagem de senha redefinida com sucesso.")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.redefinirSenha(request.getToken(), request.getNovaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
