package com.cefetmg.boinabrasa.controller;

import com.cefetmg.boinabrasa.config.TokenService;
import com.cefetmg.boinabrasa.dto.LoginRequestDTO;
import com.cefetmg.boinabrasa.dto.LoginResponseDTO;
import com.cefetmg.boinabrasa.dto.RegisterRequestDTO;
import com.cefetmg.boinabrasa.entity.Pessoa;
import com.cefetmg.boinabrasa.entity.Usuario;
import com.cefetmg.boinabrasa.repository.PessoaRepository;
import com.cefetmg.boinabrasa.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository repository;

    private final PessoaRepository pessoaRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    AuthController(AuthenticationManager authenticationManager, UsuarioRepository repository,
            PessoaRepository pessoaRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.pessoaRepository = pessoaRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO data) {
        if (this.repository.findByLogin(data.getLogin()) != null)
            return ResponseEntity.badRequest().build();

        Pessoa pessoa = pessoaRepository.findById(data.getPessoaId()).orElse(null);
        if (pessoa == null) {
            return ResponseEntity.badRequest().body("Pessoa not found");
        }

        String encryptedPassword = passwordEncoder.encode(data.getSenha());
        Usuario newUser = Usuario.builder()
                .login(data.getLogin())
                .senha(encryptedPassword)
                .role(data.getRole())
                .pessoa(pessoa)
                .build();

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenService.invalidateToken(token);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sync/{pessoaId}")
    public ResponseEntity syncUser(@PathVariable Long pessoaId, @RequestBody com.cefetmg.boinabrasa.dto.SyncUserRequestDTO data) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);
        if (pessoa == null) {
            return ResponseEntity.badRequest().body("Pessoa not found");
        }

        Usuario existingUser = repository.findByPessoaId(pessoaId);
        
        if (data.getRole() == null || data.getRole().equalsIgnoreCase("FORNECEDOR")) {
            if (existingUser != null) {
                repository.delete(existingUser);
            }
            return ResponseEntity.ok().build();
        }

        com.cefetmg.boinabrasa.entity.Role newRole = com.cefetmg.boinabrasa.entity.Role.valueOf(data.getRole().toUpperCase());

        if (existingUser != null) {
            existingUser.setLogin(data.getLogin());
            existingUser.setRole(newRole);
            if (data.getSenha() != null && !data.getSenha().isEmpty()) {
                existingUser.setSenha(passwordEncoder.encode(data.getSenha()));
            }
            repository.save(existingUser);
        } else {
            if (data.getSenha() == null || data.getSenha().isEmpty()) {
                return ResponseEntity.badRequest().body("Senha is required for new user");
            }
            Usuario newUser = Usuario.builder()
                    .login(data.getLogin())
                    .senha(passwordEncoder.encode(data.getSenha()))
                    .role(newRole)
                    .pessoa(pessoa)
                    .build();
            repository.save(newUser);
        }
        return ResponseEntity.ok().build();
    }
}
