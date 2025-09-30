package com.example.SmartBuy.repository;

import com.example.SmartBuy.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
