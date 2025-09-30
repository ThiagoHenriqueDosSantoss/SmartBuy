package com.example.SmartBuy.controller;

import com.example.SmartBuy.dto.Produto.ProdutoDTO;
import com.example.SmartBuy.entities.Produto;
import com.example.SmartBuy.service.ProdutoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProdutoController {


    private ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService service){
        this.produtoService = service;
    }

    @GetMapping("/produto")
    public List<ProdutoDTO> buscarProdutos() {
        try{
            List<ProdutoDTO> response =  produtoService.buscarProdutos();
            if(response == null){
                throw new BadRequestException("Falha ao listar produtos");
            }
            else{
                return response;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/produto")
    public Produto adicionarProduto(@RequestBody ProdutoDTO dto){
        try{
            Produto res = produtoService.adicionarProdutos(dto);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
