package de.volodymyr.learning.repository;

import de.volodymyr.learning.Main;
import de.volodymyr.learning.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonWriter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static DateTimeFormatter getFormatter(){
        return FORMATTER;
    }


    public static String jsonConverter(Task obj) {
        String creationTime = obj.createdAt().truncatedTo(ChronoUnit.SECONDS)
                .format(FORMATTER);
        String updateTime = obj.updatedAt().truncatedTo(ChronoUnit.SECONDS)
                .format(FORMATTER);
        return "{" +
                "\"id\": " + obj.id() + "," +
                "\"description\": \"" + obj.description() + "\"," +
                "\"status\": \"" + obj.status() + "\"," +
                "\"createdAt\": \"" + creationTime + "\"," +
                "\"updatedAt\": \"" + updateTime + "\"" +
                "}";

    }

    public static String preparingListToWriting(List<Task> tasks){
        return tasks.stream()
                .map(JsonWriter::jsonConverter)
                .collect(Collectors.joining(","));
    }


    public static void jsonTaskWriter(String string) {
        try {
                Files.writeString(Main.getFilePath(),  "[" + string + "]");
        } catch (IOException ioException) {
            System.out.println(Arrays.toString(ioException.getStackTrace()));
        }
    }

}
// Нужно разделить эту задачу: Прочитать файл -> то что внутри квадратных скобок -> добавить новую строку -> добавить квадратные скобки!
