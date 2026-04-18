package de.volodymyr.learning.service;

import de.volodymyr.learning.model.Task;
import de.volodymyr.learning.model.TaskStatus;
import de.volodymyr.learning.repository.JsonReader;
import de.volodymyr.learning.repository.JsonWriter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskHandler {


    public static void handleAdd(String description) {
        List<Task> currentListOfTasks = JsonReader.loadAll();
        int nextId;
        if (currentListOfTasks.isEmpty()) {
            nextId = 1;
        } else
            nextId = currentListOfTasks.stream()
                    .mapToInt(Task::id)
                    .max().orElse(1) + 1;

        currentListOfTasks.add(new Task(nextId, description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now()));
        JsonWriter.jsonTaskWriter(JsonWriter.preparingListToWriting(currentListOfTasks));

    }

    public static void handleList(TaskStatus filterStatus) {
        List<Task> tasks = JsonReader.loadAll();

        if (tasks.isEmpty()) {
            System.out.println("Your list of Tasks is empty! Chill");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-3s | %-12s | %-20s | %-20s | %s%n ", "ID", "Status", "Description", "Created", "Updated");
        System.out.println("---------------------------------------------------------------------------------------");

        List<Task> finalList;

        if (filterStatus == null)
            finalList = tasks;
        else
            finalList = tasks.stream()
                    .filter(task -> task.status() == filterStatus)
                    .toList();


        for (Task task : finalList) {
            String iconOfStatus = switch (task.status()) {
                case IN_PROGRESS -> "[>]";
                case TODO -> "[]";
                case DONE -> "[X]";
            };

            System.out.printf("%-3s | %-12s | %-20s | %-20s | %s%n",
                    task.id(),
                    iconOfStatus + " " + task.status(),
                    task.description(),
                    task.createdAt().format(formatter),
                    task.updatedAt().format(formatter)
            );

        }
        System.out.println("----------------------------------------------------------------------------------------");


    }

    public static void handleDelete(int id) {
        List<Task> currentTasks = JsonReader.loadAll();

        boolean resultOfRemoving = currentTasks.removeIf(task -> task.id() == id);

        if (!resultOfRemoving)
            System.out.println("Error: Task with ID:" + id + " not found");
        else {
            JsonWriter.jsonTaskWriter(JsonWriter.preparingListToWriting(currentTasks));
            System.out.println("Task " + id + " deleted successfully!");
        }


    }

    public static void handleUpdate(int id, String newDescription, TaskStatus newStatus) {
        List<Task> currentListTasks = JsonReader.loadAll();
        int index = findTaskIndex(currentListTasks, id);
        TaskStatus finalStatus;
        String finalDescription;

        if (index != -1) {
            Task oldTask = currentListTasks.get(index);
            if (newDescription == null)
                finalDescription = oldTask.description();
            else
                finalDescription = newDescription;

            if (newStatus == null)
                finalStatus = oldTask.status();
            else
                finalStatus = newStatus;


            currentListTasks.set(index, new Task(oldTask.id(), finalDescription, finalStatus, oldTask.createdAt(), LocalDateTime.now()));
            JsonWriter.jsonTaskWriter(JsonWriter.preparingListToWriting(currentListTasks));
            System.out.println("Success: Task-List was updated");
        } else
            System.out.println("Error: Such ID doesn't exist");


    }


    private static int findTaskIndex(List<Task> currentListTasks, int id) {
        int index = -1;

        for (int i = 0; i < currentListTasks.size(); i++) {
            if (currentListTasks.get(i).id() == id) {
                index = i;
                break;
            }
        }
        return index;
    }


}