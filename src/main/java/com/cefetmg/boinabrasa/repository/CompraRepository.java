package com.cefetmg.boinabrasa.repository;

import com.cefetmg.boinabrasa.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
}