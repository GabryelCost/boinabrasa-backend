package com.cefetmg.boinabrasa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    // Toda venda pertence a uma Pessoa (idPessoaFisica no banco)
    // @ManyToOne porque várias vendas podem apontar para a mesma pessoa
    // FetchType.LAZY evita carregar a Pessoa inteira sempre que uma Venda é buscada,
    // só carrega quando alguém realmente chamar getPessoa()
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa_fisica", nullable = false)
    private Pessoa pessoa;

    // Uma Venda tem vários itens (VendaProduto).
    // mappedBy = "venda" indica que quem É DONO da relação no banco (quem tem a FK)
    // é a classe VendaProduto, não a Venda. A Venda não tem coluna nova no banco por causa disso
    // cascade = ALL: se eu salvar/excluir uma Venda, os itens dela são salvos/excluídos junto
    // orphanRemoval = true: se eu remover um item dessa lista em memória, ele é apagado do banco tambem
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaProduto> itens = new ArrayList<>();
}