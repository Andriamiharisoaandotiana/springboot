package niraina.loc.todo.services;

import niraina.loc.todo.entities.Todo;
import niraina.loc.todo.repository.TodoRepository;
import niraina.loc.todo.shared.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public ApiResponse createTodo(Todo todo){
        this.todoRepository.save(todo);
        return new ApiResponse("Success add task!");
    }

    public List<Todo> getAllTodo(){
        return this.todoRepository.findAll();
    }

    public Todo getTodo(int id){
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);
        return optionalTodo.orElse(null);
    }

    public ApiResponse updateTodo(int id, Todo updatedTodo) {
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            Todo existingTodo = optionalTodo.get();

            existingTodo.setTitle(updatedTodo.getTitle());
            existingTodo.setIs_completed(updatedTodo.getIs_completed());

            this.todoRepository.save(existingTodo);

            return new ApiResponse("Task updated successfully!");
        } else {
            return new ApiResponse("Task not found!");
        }
    }

    public ApiResponse deleteTodo(int id){
        Optional<Todo> optionalTodo = this.todoRepository.findById(id);

        if(optionalTodo.isPresent()) {
            this.todoRepository.deleteById(id);
            return new ApiResponse("Task deleted successfully!");
        }else {
            return new ApiResponse("Task not found!");
        }
    }

}
