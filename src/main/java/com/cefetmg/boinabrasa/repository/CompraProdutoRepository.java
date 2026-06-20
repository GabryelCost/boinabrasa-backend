package com.cefetmg.boinabrasa.repository;

import com.cefetmg.boinabrasa.entity.CompraProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraProdutoRepository extends JpaRepository<CompraProduto, Long> {
}
