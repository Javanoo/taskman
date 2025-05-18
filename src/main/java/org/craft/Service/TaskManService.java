package org.craft.Service;

import org.craft.Model.Task;
import org.craft.Repository.TaskManMysqlDb;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskManService {
    public TaskManMysqlDb taskManMysqlDb;
    public final static Scanner input = new Scanner(System.in);
    public LinkedHashSet<Task> currentDbTasks = new LinkedHashSet<>();
    public ArrayList<Task> pendingDbTasks = new ArrayList<>();
    public HashMap<Integer, Task> currentTaskMap = new HashMap<>();
    public ExecutorService threadPool = Executors.newCachedThreadPool();
    public static final TaskManLog logger = new TaskManLog();

    public TaskManService(){
        try {
            taskManMysqlDb = new TaskManMysqlDb(null, "jdbc:mysql://localhost/taskman",
                    "taskagent", "taskagent00");
            refreshCurrentDb();
            pendingDbTasks = new ArrayList<>(currentDbTasks);

                for (Task task : currentDbTasks) {
                    currentTaskMap.put(task.getId(), task);
                }
            logger.inform("TaskManService initialised");
        } catch (ConnectException e) {
            logger.error("Failed to connect to database.");
        }catch (Exception e) {
            logger.error("something went very bad.");
            throw new RuntimeException(e);
        }
    }

    private void refreshCurrentDb() {
        try {
            currentDbTasks.clear();
            currentDbTasks = (taskManMysqlDb.getTasks() == null) ? new LinkedHashSet<>() : taskManMysqlDb.getTasks();
            logger.inform("updated database.");
        } catch (SQLException e) {
            logger.error("failed to update database.");
            throw new RuntimeException(e);
        }
    }

    public void addTaskToDb(){
        synchronized (pendingDbTasks) {
            try {
                Task task = new Task();
                System.out.println("Enter title :");
                task.setTitle(input.nextLine());
                System.out.print("Set Priority[0: low, 1: normal, 2: high] : ");
                task.setPriority(input.nextInt());

                taskManMysqlDb.addTasks(task);
                currentTaskMap.put(task.getId(), task);
                logger.inform("Task [" + task.getId() + "] " + task.getTitle() + " added.");
                refreshCurrentDb();
            } catch (Exception e) {
                logger.error("Failed to add task to Database." + e);
            }
        }
    }

    public void editTaskInDb(){
        synchronized (currentDbTasks) {
            try {
                for(Task task : currentDbTasks){
                    System.out.println(task);
                }
                System.out.println("Select task (i.e 1): ");
                int taskChoice = Integer.parseInt(input.nextLine().strip());
                Task toEdit = currentTaskMap.get(taskChoice);
                System.out.println("you've chosen : " );
                System.out.println("[ "+ toEdit.getId() + " | " +
                        toEdit.getTitle() + " | " +
                        toEdit.getPriority() + " | " +
                        toEdit.isDone() + " | " +
                        toEdit.getCreatedOn() + " ]");
                System.out.println("What would you like to edit? name (1), priority(2), done(3), all(4) : ");
                switch (input.nextInt()){
                    case 1 : {
                        System.out.print("Enter title : ");
                        toEdit.setTitle(input.nextLine());
                        logger.inform("editing task...");
                        taskManMysqlDb.editTask(toEdit);
                        logger.inform("task edited.");
                        System.out.println("task edited");
                        break;
                    }
                    case 2 : {
                        System.out.print("Set priority [0 low, 1 normal, 2 high] : ");
                        toEdit.setPriority(input.nextInt());
                        logger.inform("editing task.");
                        taskManMysqlDb.editTask(toEdit);
                        logger.inform("task edited.");
                        System.out.println("task edited");
                        break;
                    }
                    case 3 : {
                        System.out.print("Done? (y/n) : ");
                        String doneChoice = input.next();
                        if(doneChoice.charAt(0) == 'y' || doneChoice.charAt(0) == 'n') {
                            toEdit.setDone((doneChoice.charAt(0) == 'y'));
                            logger.inform("editing task.");
                            taskManMysqlDb.editTask(toEdit);
                            logger.inform("task edited.");
                            System.out.println("task edited");
                            break;
                        }
                        break;
                    }
                    case 4 : {
                        System.out.print("Enter title : ");
                        toEdit.setTitle(input.nextLine());
                        System.out.print("Set priority [0 low, 1 normal, 2 high] : ");
                        toEdit.setPriority(input.nextInt());
                        System.out.print("Done? (y/n) : ");
                        String doneChoice = input.next();
                        if(doneChoice.charAt(0) == 'y' || doneChoice.charAt(0) == 'n') {
                            toEdit.setDone((doneChoice.charAt(0) == 'y'));
                            logger.inform("editing task.");
                            taskManMysqlDb.editTask(toEdit);
                            logger.inform("task edited.");
                            System.out.println("task edited");
                            break;
                        }
                        break;
                    }
                    default: {
                        throw new RuntimeException();
                    }
                }
                refreshCurrentDb();

            } catch (Exception e) {
                logger.error("Failed to edit task.");
            }
        }

    }

    public void deleteTaskFromDb() {
        synchronized (currentDbTasks) {
            try {
                for (Task task : currentDbTasks) {
                    System.out.println(task);
                }
                System.out.println("Select task (i.e 1): ");
                int taskChoice = Integer.parseInt(input.nextLine().strip());
                Task toDelete = currentTaskMap.get(taskChoice);

                logger.inform("deleting task [" + toDelete.getId() + "]. ");
                taskManMysqlDb.deleteTask(toDelete);
                refreshCurrentDb();
            } catch (Exception e) {
                logger.error("Failed to delete task.");
            }
        }
    }

    public void viewTasks(){
        System.out.println("Showing all(" + currentDbTasks.size()+ ") tasks " );
        logger.inform("viewing tasks ... ");
        for(Task task : currentDbTasks){
            System.out.println(task);
        }
    }

    public void start()throws RuntimeException{
        int action = 0;
        String userInput = "";
        boolean run = true;
        while (run){
            prompt();
            try {
                userInput = input.nextLine().strip();
                action =Integer.parseInt(userInput.isEmpty() ? input.nextLine().strip() : userInput);
                if (action == 1)
                    addTaskToDb();
                else if (action == 2)
                    editTaskInDb();
                else if (action == 3)
                    deleteTaskFromDb();
                else if (action == 4)
                    viewTasks();
                else if (action == 5) {
                    logger.inform("System closed at [" + new Date() + "].");
                    System.out.println("bye!");
                    run = false;
                } else
                    System.out.println("Choose from the options a number (i.e. 1)");
            } catch (RuntimeException e) {
                logger.inform("Choose a number from the options.");
            }
        }
    }

    private void prompt(){
        System.out.printf("%s\n%s\n%s\n%s\n%s\n",
                "1. create new task",
                "2. edit a task",
                "3. delete a task",
                "4. view all tasks",
                "5. exit");
        System.out.print("Do ? : ");
    }

}