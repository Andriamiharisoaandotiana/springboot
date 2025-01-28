package niraina.loc.todo.controllers;

import niraina.loc.todo.entities.Message;
import niraina.loc.todo.entities.Todo;
import niraina.loc.todo.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "chat")
public class ChatController {
    private final MessageService service;

    public ChatController(MessageService service) {
        this.service = service;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Message> getAll(){
        return this.service.getAllMessage();
    }

    @PostMapping
    public Message handleChat(@RequestBody Message userMessage) {
        try {
            String question = userMessage.getMessage();

            // Construire la commande d'exécution du script Python avec la question
            Process process = Runtime.getRuntime().exec("python \"E:/chatExemple/Nouveau dossier/todo/todo/src/main/java/niraina/loc/todo/controllers/chat.py\" \"" + question + "\"");

            // Lire la sortie standard du processus
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String response = reader.readLine();

            // Si la sortie est vide, essayer de lire la sortie d'erreur
            if (response == null || response.isEmpty()) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line).append("\n");
                }
                if (errorResponse.length() > 0) {
                    System.out.println("Erreur du processus Python : " + errorResponse.toString());
                }
                response = "Aucune réponse trouvée.";
            }

            // Mettre la réponse dans l'objet Message
            userMessage.setResponse(response);

            // Sauvegarder et retourner le message
            return service.saveMessage(userMessage);

        } catch (Exception e) {
            e.printStackTrace(); // Afficher l'exception dans les logs
            userMessage.setResponse("Erreur lors du traitement de la demande.");
            return service.saveMessage(userMessage);
        }
    }

}
