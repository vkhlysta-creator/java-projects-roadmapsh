package de.volodymyr.learning;




import java.time.LocalDateTime;



public class Main {
    public record Task(int id, String description, TaskStatus status, LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
    }

    enum TaskStatus {TODO, IN_PROGRESS, DONE}




    public static void main(String[] args) {
        System.out.println("Hello World");

        Task newTask = new Task(1, "Buy a cat", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());

        JsonWriter.jsonTaskWriter(JsonWriter.jsonConverter(newTask));

    }
}