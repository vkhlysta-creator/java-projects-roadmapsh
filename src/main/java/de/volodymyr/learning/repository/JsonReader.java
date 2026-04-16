package de.volodymyr.learning.repository;

import de.volodymyr.learning.model.Task;
import de.volodymyr.learning.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static void main(String[] args) {

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
                transformedString = transformedString.substring(endIndexForParsing + endIndexOffset);  // +2 because the "\," left in a new String
                allStringParsed.add(parsedString);

        }
        return allStringParsed;
    }


}
