package niraina.loc.todo.services;

import niraina.loc.todo.entities.Message;
import niraina.loc.todo.entities.Todo;
import niraina.loc.todo.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public List<Message> getAllMessage(){
        return this.repository.findAll();
    }
}
