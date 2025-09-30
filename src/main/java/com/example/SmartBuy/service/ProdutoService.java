package com.example.SmartBuy.service;

import com.example.SmartBuy.dto.Produto.ProdutoDTO;
import com.example.SmartBuy.entities.Produto;
import com.example.SmartBuy.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProdutoService {
    private final RestTemplate restTemplate;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoService(){
        this.restTemplate = new RestTemplate();
    }

    public List<ProdutoDTO> buscarProdutos() {
        try {
            String url = "https://fakestoreapi.com/products";

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

            List<Map<String, Object>> body = response.getBody();
            if (body == null) return new ArrayList<>();

            List<ProdutoDTO> produtos = new ArrayList<>();
            for (Map<String, Object> item : body) {
                ProdutoDTO dto = new ProdutoDTO();

                dto.setId(((Number) item.get("id")).longValue());
                dto.setTitulo((String) item.get("title"));
                dto.setPreco(((Number) item.get("price")).doubleValue());
                dto.setDescricao((String) item.get("description"));
                dto.setCategoria((String) item.get("category"));
                dto.setImagem((String) item.get("image"));
                dto.setLinkPermanente("https://fakestoreapi.com/products/" + item.get("id"));

                Map<String, Object> ratingMap = (Map<String, Object>) item.get("rating");
                if (ratingMap != null) {
                    ProdutoDTO.AvaliacaoDTO ratingDTO = new ProdutoDTO.AvaliacaoDTO();
                    ratingDTO.setNota(((Number) ratingMap.get("rate")).doubleValue());
                    ratingDTO.setQuantidade(((Number) ratingMap.get("count")).intValue());
                    dto.setAvaliacao(ratingDTO);
                }

                produtos.add(dto);
            }

            return produtos;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public Produto adicionarProdutos(ProdutoDTO dto){
        try{
            Produto produto = new Produto();
            produto.setTitulo(dto.getTitulo());
            produto.setPreco(dto.getPreco());
            produto.setDescricao(dto.getDescricao());
            produto.setCategoria(dto.getCategoria());
            produto.setImagem(dto.getImagem());

            produtoRepository.save(produto);

            return produto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
