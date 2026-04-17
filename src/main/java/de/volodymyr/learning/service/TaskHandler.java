package de.volodymyr.learning.service;

import de.volodymyr.learning.model.Task;
import de.volodymyr.learning.model.TaskStatus;
import de.volodymyr.learning.repository.JsonReader;
import de.volodymyr.learning.repository.JsonWriter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskHandler {

    public static void main(String[] args) {
        try {
            handleList();
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException");
        }
    }


    public static void handleAdd(String description) throws NoSuchElementException{
        List<Task> currentListOfTasks = JsonReader.loadAll();
        int nextId;
        if (currentListOfTasks.isEmpty()){
            nextId = 1;
        }
        else
            nextId = currentListOfTasks.stream()
                    .mapToInt(Task::id)
                    .max().orElseThrow(NoSuchElementException::new) + 1;

        currentListOfTasks.add(new Task(nextId, description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now()));
        JsonWriter.jsonTaskWriter(JsonWriter.preparingListToWriting(currentListOfTasks));

    }

    public static void handleList(){
        List<Task> tasks = JsonReader.loadAll();

        if (tasks.isEmpty()){
            System.out.println("Your list of Tasks is empty! Chill");
        }

        DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        System.out.println("-------------------------------------------------------------------");
        System.out.printf("%-3s | %-12s | %-20s | %s%n", "ID"  , "Status", "Description" , "Created");
        System.out.println("-------------------------------------------------------------------");




        for (Task task : tasks){
            String iconOfStatus = switch (task.status()){
                case IN_PROGRESS -> "[>]";
                case TODO -> "[]";
                case DONE -> "[X]";
            };

            System.out.printf("%-3s | %-12s | %-20s | %s%n",
                    task.id(),
                    iconOfStatus +" " +  task.status(),
                    task.description(),
                    task.createdAt().format(Formatter)
                    );

        }
        System.out.println("-------------------------------------------------------------------");

    }




}
