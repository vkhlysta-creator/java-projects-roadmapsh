Task Tracker CLI
A robust and efficient Command Line Interface (CLI) application for managing tasks, built with Java. This project follows the specifications provided by the roadmap.sh backend development track.
(https://roadmap.sh/projects/task-tracker)

🚀 Features
Full CRUD Functionality: Create, Read, Update, and Delete tasks seamlessly.

State Management: Track task progress with statuses: todo, in-progress, and done.

Smart Filtering: List all tasks or filter them by their current status.

Persistent Storage: Data is automatically serialized and saved to a text.json file.

Input Validation: Robust error handling for incorrect IDs, missing arguments, and invalid statuses.

🛠 Technologies Used
Java 17+: Leveraging modern features like Records, Switch Expressions, and Stream API.

JSON: Used for lightweight local data storage.

📋 Usage Guide
Basic Commands

Add a new task:


java Main add "Buy groceries"
List all tasks:

java Main list
Filter tasks by status:

java Main list done
java Main list in-progress
java Main list todo
Update task description:

java Main update 1 "Buy groceries and coffee"
Change task status:

java Main mark-in-progress 1
java Main mark-done 1
Delete a task:


java Main delete 1
🏗 Project Architecture

Main.java: The entry point. Handles CLI argument parsing and input validation.

TaskHandler.java: The core engine. Manages the logic for modifying the task list and interacting with the data layer.

Task.java: The data model, implemented as an immutable Java Record.

JsonReader.java & JsonWriter.java: Specialized components for file I/O and JSON serialization.

🔧 Installation
Clone the repository to your local machine.

Compile the source files:

Bash
javac *.java
Run the application using the commands listed in the Usage Guide.
