package niraina.loc.todo.controllers;

import niraina.loc.todo.entities.Todo;
import niraina.loc.todo.services.TodoService;
import niraina.loc.todo.shared.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(path = "todos")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Todo> getAll(){
        return this.todoService.getAllTodo();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ApiResponse createTodo(@RequestBody Todo todo){
        return this.todoService.createTodo(todo);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public Todo getTodo(@PathVariable int id){
        return this.todoService.getTodo(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ApiResponse updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        return this.todoService.updateTodo(id, todo);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    public ApiResponse deleteTodo(@PathVariable int id) {
        return this.todoService.deleteTodo(id);
    }
}
