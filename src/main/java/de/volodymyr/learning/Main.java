package de.volodymyr.learning;




import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;


public class Main {
    public record Task(int id, String description, TaskStatus status, LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
    }

    enum TaskStatus {TODO, IN_PROGRESS, DONE}
    static Path filePath = Paths.get("text.json");



    public static void main(String[] args) {
        try {
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException faex) {
            System.out.println("File was already created");
        } catch (IOException ioException) {
            System.out.println(Arrays.toString(ioException.getStackTrace()));
        }

        Task newTask = new Task(1, "Buy a cat", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());

        JsonWriter.jsonTaskWriter(JsonWriter.jsonConverter(newTask));

        try {
            String jsonString = Files.readString(Main.filePath);
            Task parsedTask = JsonReader.readTask(jsonString);
            System.out.println(parsedTask.description());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}