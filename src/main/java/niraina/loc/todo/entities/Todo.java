package niraina.loc.todo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int is_completed;

    public Todo() {
    }

    public Todo(int id, String title, int is_completed) {
        this.id = id;
        this.title = title;
        this.is_completed = is_completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(int is_completed) {
        this.is_completed = is_completed;
    }
}
