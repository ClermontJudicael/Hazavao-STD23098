package com.hazavao.demo.endpoint.RestController;

import com.hazavao.demo.datastructure.DTO.ChatRequest;
import com.hazavao.demo.datastructure.DTO.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatController {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        // create a request
        ChatRequest request = new ChatRequest(model, prompt);

        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/hazavao")
    public String hazavao(@RequestParam String teny) {
        // Créer un prompt spécifique pour obtenir la définition en malgache
        String prompt = String.format(
                "Omeo ny famaritana amin'ny teny malagasy ho an'ny teny '%s'. " +
                        "Raha tsy fantatrao ny teny dia lazao hoe 'Tsy hita ny famaritana'. " +
                        "Valiny fotsiny ny famaritana, tsy mila fanazavana fanampiny.",
                teny
        );

        // Créer la requête
        ChatRequest request = new ChatRequest(model, prompt);

        try {
            // Appeler l'API
            ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return "Tsy afaka nahazo valiny";
            }

            // Retourner la définition
            String definition = response.getChoices().get(0).getMessage().getContent();

            // Nettoyer la réponse si nécessaire
            return definition.trim();

        } catch (Exception e) {
            return "Nisy olana tamin'ny fitadiavana ny famaritana: " + e.getMessage();
        }
    }
}