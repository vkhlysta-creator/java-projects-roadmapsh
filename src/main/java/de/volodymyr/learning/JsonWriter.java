package de.volodymyr.learning;

import de.volodymyr.learning.Main.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

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

    public static void jsonTaskWriter(String string) {
        try {
            Files.writeString(Main.filePath, string);
        } catch (IOException ioException) {
            System.out.println(Arrays.toString(ioException.getStackTrace()));
        }
    }

}
