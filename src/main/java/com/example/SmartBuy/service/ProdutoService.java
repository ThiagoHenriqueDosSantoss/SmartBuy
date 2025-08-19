package com.example.SmartBuy.service;

import com.example.SmartBuy.dto.Produto.ProdutoDTO;
import com.example.SmartBuy.entities.Produto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {
    private final RestTemplate restTemplate;

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
            List<ProdutoDTO> produtos = new ArrayList<>();

            for (Map<String, Object> item : body) {
                ProdutoDTO dto = new ProdutoDTO();

                dto.setId(((Number) item.get("id")).longValue());
                dto.setTitle((String) item.get("title"));
                dto.setPrice(((Number) item.get("price")).doubleValue());
                dto.setDescription((String) item.get("description"));
                dto.setCategory((String) item.get("category"));
                dto.setImage((String) item.get("image"));
                dto.setPermalink("https://fakestoreapi.com/products/" + item.get("id"));

                // Mapeia o objeto aninhado "rating"
                Map<String, Object> ratingMap = (Map<String, Object>) item.get("rating");
                if (ratingMap != null) {
                    ProdutoDTO.RatingDTO ratingDTO = new ProdutoDTO.RatingDTO();
                    ratingDTO.setRate(((Number) ratingMap.get("rate")).doubleValue());
                    ratingDTO.setCount(((Number) ratingMap.get("count")).intValue());
                    dto.setRating(ratingDTO);
                }

                produtos.add(dto);
            }

            return produtos;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



}
