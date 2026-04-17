package de.volodymyr.learning;





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
                System.out.println("We are handling list for you");
                TaskHandler.handleList();
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
                    TaskHandler.handleDelete(args[1]);
                }
            }
            default -> System.out.println("Unknown command: " + command);
        }
    }










    }
