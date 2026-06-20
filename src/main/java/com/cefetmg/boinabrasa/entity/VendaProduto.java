package com.cefetmg.boinabrasa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "venda_produto")
public class VendaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    // "valor" aqui é o preço do produto NO MOMENTO da venda.
    // Não usamos o preço atual do Produto, porque se o preço do produto mudar
    // no futuro, o histórico dessa venda não pode mudar junto.
    @Column(nullable = false)
    private BigDecimal valor;

    // Dono da relação com Venda. É essa coluna (id_venda) que aparece na tabela venda_produto.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;

    // Cada item se refere a um Produto específico.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;
}