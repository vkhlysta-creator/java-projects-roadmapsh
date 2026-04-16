package de.volodymyr.learning;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static void main(String[] args) {

    }
    public static Main.Task readTask(String jsonString){
            int beginIndexID = jsonString.indexOf("id\":") + 5; // taking id so we have 5 characters: i,d,",:,
            int endIndexId = jsonString.indexOf(",");
            int id = Integer.parseInt(jsonString.substring(beginIndexID,endIndexId));

            String transformedString = jsonString.substring(endIndexId + 2);
            List<String> allStringParsed = new ArrayList<>();

            while (!transformedString.isEmpty()){


                int beginIndexForParsing = transformedString.indexOf("\": \"") + 4;
                int endIndexForParsing = transformedString.indexOf("\",");
                if (endIndexForParsing == -1){
                    endIndexForParsing = transformedString.indexOf("\"}");
                }
                    String parsedString = transformedString.substring(beginIndexForParsing, endIndexForParsing);
                    transformedString = transformedString.substring(endIndexForParsing + 2);  // +2 because the "\," left in a new String
                    allStringParsed.add(parsedString);

            }

            LocalDateTime createdAt = LocalDateTime.parse(allStringParsed.get(2), JsonWriter.getFormatter());
            LocalDateTime updatedAt = LocalDateTime.parse(allStringParsed.get(3), JsonWriter.getFormatter());

            Main.TaskStatus status = Enum.valueOf(Main.TaskStatus.class, allStringParsed.get(1));

            return new Main.Task(id, allStringParsed.getFirst(), status, createdAt, updatedAt);





    }



}
