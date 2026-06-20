package com.cefetmg.boinabrasa.service;

import com.cefetmg.boinabrasa.dto.CompraRequestDTO;
import com.cefetmg.boinabrasa.dto.CompraResponseDTO;
import com.cefetmg.boinabrasa.entity.Compra;
import com.cefetmg.boinabrasa.entity.Pessoa;
import com.cefetmg.boinabrasa.repository.CompraRepository;
import com.cefetmg.boinabrasa.repository.PessoaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final PessoaRepository pessoaRepository;

    public CompraService(CompraRepository compraRepository, PessoaRepository pessoaRepository) {
        this.compraRepository = compraRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public List<CompraResponseDTO> listarTodos() {
        List<Compra> compras = compraRepository.findAll();
        return compras.stream()
                .map(c -> new CompraResponseDTO(
                        c.getId(),
                        c.getDataCompra(),
                        c.getValorCompra(),
                        c.getFornecedor().getNome()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CompraResponseDTO criar(CompraRequestDTO request) {
        Pessoa fornecedor = pessoaRepository.findById(request.getIdFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado com o ID: " + request.getIdFornecedor()));

        Compra compra = new Compra();
        compra.setDataCompra(request.getDataCompra());
        compra.setValorCompra(request.getValorCompra());
        compra.setFornecedor(fornecedor);

        Compra c = compraRepository.save(compra);

        return new CompraResponseDTO(c.getId(), c.getDataCompra(), c.getValorCompra(), c.getFornecedor().getNome());
    }

    @Transactional
    public CompraResponseDTO alterar(Long id, CompraRequestDTO request) {
        Compra compraExistente = compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Compra não encontrada com o ID: " + id));

        Pessoa fornecedor = pessoaRepository.findById(request.getIdFornecedor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Fornecedor não encontrado com o ID: " + request.getIdFornecedor()));

        compraExistente.setDataCompra(request.getDataCompra());
        compraExistente.setValorCompra(request.getValorCompra());
        compraExistente.setFornecedor(fornecedor);

        Compra c = compraRepository.save(compraExistente);

        return new CompraResponseDTO(c.getId(), c.getDataCompra(), c.getValorCompra(), c.getFornecedor().getNome());
    }

    @Transactional
    public void excluir(Long id) {
        if (!compraRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não é possível excluir. Compra não encontrada com o ID: " + id);
        }
        compraRepository.deleteById(id);
    }
}