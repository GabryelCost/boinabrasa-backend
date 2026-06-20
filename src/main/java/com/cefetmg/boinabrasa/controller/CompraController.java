package com.cefetmg.boinabrasa.controller;

import com.cefetmg.boinabrasa.dto.CompraRequestDTO;
import com.cefetmg.boinabrasa.dto.CompraResponseDTO;
import com.cefetmg.boinabrasa.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarTodos() {
        List<CompraResponseDTO> compras = compraService.listarTodos();
        return ResponseEntity.ok(compras);
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> criar(@Valid @RequestBody CompraRequestDTO request) {
        CompraResponseDTO novaCompra = compraService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCompra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> alterar(@PathVariable Long id, @Valid @RequestBody CompraRequestDTO request) {
        CompraResponseDTO compraAtualizada = compraService.alterar(id, request);
        return ResponseEntity.ok(compraAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        compraService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}