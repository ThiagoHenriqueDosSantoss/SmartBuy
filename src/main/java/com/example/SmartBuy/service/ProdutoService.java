package com.example.SmartBuy.service;

import com.example.SmartBuy.dto.Produto.ProdutoDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProdutoService {
    private final RestTemplate restTemplate;
    private static final long INTERVALO_MINIMO_MS = 5000;
    private final Map<String, String> cacheDeTraducoes = new ConcurrentHashMap<>();

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

            //Coletar todos os textos únicos a serem traduzidos
            Set<String> textosParaTraduzir = new HashSet<>();
            for (Map<String, Object> item : body) {
                textosParaTraduzir.add((String) item.get("title"));
                textosParaTraduzir.add((String) item.get("description"));
                textosParaTraduzir.add((String) item.get("category"));
            }

            Map<String, String> traducoes = traduzirLote(textosParaTraduzir, "en", "pt");

            //Criar DTOs com traduções já feitas
            List<ProdutoDTO> produtos = new ArrayList<>();
            for (Map<String, Object> item : body) {
                ProdutoDTO dto = new ProdutoDTO();

                String title = (String) item.get("title");
                String description = (String) item.get("description");
                String category = (String) item.get("category");

                dto.setId(((Number) item.get("id")).longValue());
                dto.setTitle(traducoes.get(title));
                dto.setPrice(((Number) item.get("price")).doubleValue());
                dto.setDescription(traducoes.get(description));
                dto.setCategory(traducoes.get(category));
                dto.setImage((String) item.get("image"));
                dto.setPermalink("https://fakestoreapi.com/products/" + item.get("id"));

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
    private Map<String, String> traduzirLote(Set<String> textos, String de, String para) {
        Map<String, String> resultados = new ConcurrentHashMap<>();

        textos.parallelStream().forEach(textoOriginal -> {
            String texto = textoOriginal;
            String chave = de + "->" + para + "::" + texto;

            // Cache
            if (cacheDeTraducoes.containsKey(chave)) {
                resultados.put(textoOriginal, cacheDeTraducoes.get(chave));
            } else {
                try {
                    String encoded = URLEncoder.encode(texto, StandardCharsets.UTF_8.toString());
                    String url = String.format("https://lingva.ml/api/v1/%s/%s/%s", de, para, encoded);
                    ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

                    Map<String, Object> body = response.getBody();
                    String traducao = body != null ? (String) body.get("translation") : texto;

                    cacheDeTraducoes.put(chave, traducao);
                    resultados.put(textoOriginal, traducao);

                    Thread.sleep(INTERVALO_MINIMO_MS);

                } catch (Exception e) {
                    e.printStackTrace();
                    resultados.put(textoOriginal, texto);
                }
            }
        });

        return resultados;
    }
}
