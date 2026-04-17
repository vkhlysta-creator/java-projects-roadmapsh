package de.volodymyr.learning.repository;

import de.volodymyr.learning.Main;
import de.volodymyr.learning.model.Task;
import de.volodymyr.learning.model.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonReader {

    public static void main(String[] args) {
        List<Task> tasks = loadAll();
        System.out.println(tasks);

    }

    public static Task readTask(String jsonString){
            int idOffset = "id\":".length();
            int beginIndexID = jsonString.indexOf("id\":") + idOffset;
            int endIndexId = jsonString.indexOf(",");
            int id = Integer.parseInt(jsonString.substring(beginIndexID,endIndexId).trim());

        List<String> allStringParsed = getStrings(jsonString, endIndexId);

        LocalDateTime createdAt = LocalDateTime.parse(allStringParsed.get(2), JsonWriter.getFormatter());
            LocalDateTime updatedAt = LocalDateTime.parse(allStringParsed.get(3), JsonWriter.getFormatter());

            TaskStatus status = Enum.valueOf(TaskStatus.class, allStringParsed.get(1));

            return new Task(id, allStringParsed.getFirst(), status, createdAt, updatedAt);





    }

    private static List<String> getStrings(String jsonString, int endIndexId) {
        String transformedString = jsonString.substring(endIndexId + 2);
        List<String> allStringParsed = new ArrayList<>();

        while (!transformedString.isBlank()){

            int beginIndexOffset = "\": \"".length();
            int beginIndexForParsing = transformedString.indexOf("\": \"") + beginIndexOffset;
            int endIndexOffset = "\",".length();
            int endIndexForParsing = transformedString.indexOf("\",");
            if (endIndexForParsing == -1){
                endIndexForParsing = transformedString.indexOf("\"}");
            }
                String parsedString = transformedString.substring(beginIndexForParsing, endIndexForParsing);
                transformedString = transformedString.substring(endIndexForParsing + endIndexOffset);
                allStringParsed.add(parsedString);

        }
        return allStringParsed;
    }

    public static List<Task> loadAll(){
        try {
            String allLines =  Files.readString(Main.getFilePath());
            int firstIndexOffset = "[".length();
            int indexFirstBracket = allLines.indexOf("[");
            int indexSecondBracket = allLines.indexOf("]");

            if (allLines.isBlank()){
                return new ArrayList<>();
            }

            String stringWithoutBrackets = allLines.substring(indexFirstBracket + firstIndexOffset, indexSecondBracket);
            List<String> splitedStringFinal = new ArrayList<>();
            if (stringWithoutBrackets.isBlank()){
                return new ArrayList<>();
            }
            if (stringWithoutBrackets.contains("},")) {
                String[] splitedString = stringWithoutBrackets.split("},\\{");
                splitedStringFinal = Arrays.stream(splitedString)
                        .map(str -> {
                            String clean = str.replace("{", "").replace("}", "");
                            return "{" + clean + "}";
                        })
                        .toList();
            }else
                splitedStringFinal.add(stringWithoutBrackets);

            return new ArrayList<>(splitedStringFinal.stream()
                    .map(JsonReader::readTask)
                    .toList());



        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return new ArrayList<>();
    }


}
