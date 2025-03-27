package niraina.loc.todo.controllers;

import niraina.loc.todo.entities.Message;
import niraina.loc.todo.services.MessageService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "chat")
public class ChatController {

    private final MessageService service;
    private final RestTemplate restTemplate;

    public ChatController(MessageService service, RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Message> getAll() {
        return this.service.getAllMessage();
    }

    @PostMapping
    public ResponseEntity<Message> handleChat(@RequestBody Message userMessage) {
        try {
            String question = userMessage.getMessage();
            System.out.println("📩 Question reçue : " + question);

            // Construire le payload (CORRECTION ICI)
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("question", question); // Utiliser "question" au lieu de "message"

            // Transformer en JSON pour vérification
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(requestBody);
            System.out.println("📤 Données envoyées à Flask : " + jsonPayload);

            // Définir les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Créer la requête HTTP avec le payload et les headers
            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            // URL de l'API Flask
            String flaskApiUrl = "http://127.0.0.1:5000/chat";

            // Envoyer la requête à Flask
            ResponseEntity<Map> response = restTemplate.exchange(
                    flaskApiUrl,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            // Vérifier la réponse
            System.out.println("📥 Réponse reçue de Flask : " + response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String chatbotResponse = (String) response.getBody().get("response");
                System.out.println("🤖 Réponse du chatbot : " + chatbotResponse);
                userMessage.setResponse(chatbotResponse);
            } else {
                System.out.println("❌ Erreur : réponse vide ou statut HTTP incorrect");
                userMessage.setResponse("Erreur lors de la communication avec le chatbot.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            userMessage.setResponse("Erreur lors du traitement de la demande.");
        }

        return ResponseEntity.ok(service.saveMessage(userMessage));
    }
}