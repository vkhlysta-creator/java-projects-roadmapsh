package de.volodymyr.learning;





import de.volodymyr.learning.model.TaskStatus;
import de.volodymyr.learning.service.TaskHandler;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {



    private static final Path filePath = Paths.get("text.json");

    public static Path getFilePath(){
        return filePath;
    }



    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java Main <command> [arguments]");
            System.out.println("Commands: add, list, update, delete");
            return;
        }


        String command = args[0].toLowerCase();

        switch (command) {
            case "list" -> {
                System.out.println("We are creating List for you");
                if (args.length > 1){
                    try {
                        TaskHandler.handleList(TaskStatus.valueOf(args[1].toUpperCase().replace("-", "_")));
                    } catch (IllegalArgumentException e) {
                        System.out.println(
                                "You've entered an illegal argument\n" +
                                        "You may: todo, in_progress, done, or just don't write anything to see all your tasks"

                        );
                    }

                }else
                    TaskHandler.handleList(null);
            }
            case "add" -> {

                if (args.length < 2) {
                    System.out.println("Error: Please provide a task description.");
                } else {
                    System.out.println("Adding task: " + args[1]);
                    TaskHandler.handleAdd(args[1]);
                }
            }
            case "delete" -> {

                if (args.length < 2){
                    System.out.println("Error: Please provide an ID of the task, which must be deleted");
                }
                else {
                    int parsedId = parseInt(args[1]);
                    TaskHandler.handleDelete(parsedId);

                }
            }
            case "update" ->{
                if (args.length < 3){
                    System.out.println("Error: Please provide an ID of the task,and a new description");
                }
                else {
                    int parsedId = parseInt(args[1]);
                    if (parsedId != -1)
                        TaskHandler.handleUpdate(parsedId, args[2], null);
                }
            }

            case "mark-in-progress" -> {
                if (args.length < 2){
                    System.out.println("Error: Please provide an ID");
                }
                else {
                    int parsedId = parseInt(args[1]);
                    if (parsedId != -1)
                        TaskHandler.handleUpdate(parsedId, null, TaskStatus.IN_PROGRESS);
                }
            }

            case "mark-done" -> {
                if (args.length < 2){
                    System.out.println("Error: Please provide an ID");
                }
                else {
                    int parsedId = parseInt(args[1]);
                    if (parsedId != -1)
                        TaskHandler.handleUpdate(parsedId, null, TaskStatus.DONE);

                }
            }

            default -> System.out.println("Unknown command: " + command);
        }
    }

    private static int parseInt(String string){
        try {
            return Integer.parseInt(string);
        }catch (NumberFormatException e){
            System.out.println("Error: Incorrect ID, Please provide an integer number as second element");
        }

        return -1;
    }}
