package org.craft;

import org.craft.Service.TaskManService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public App(){
        TaskManService service = new TaskManService();
        Scanner input ;

        while(true){
            //prompt user
            prompt();
            input = new Scanner(System.in);

            try {
                //process user's choice
                switch (input.nextInt()) {
                    case 1:
                        service.addTask();
                        break;
                    case 2:
                        service.editTask();
                        break;
                    case 3:
                        service.deleteTask();
                        break;
                    case 4:
                        service.viewTasks();
                        break;
                    // exit for any other action.
                    case 5:
                    default:
                        System.exit(0);
                }
            } catch (InputMismatchException e) {
                System.out.println("[Invalid Option]\nchoose (1 - 4)");
            }
        }
    }

    public void prompt(){
        System.out.printf("%s\n%s\n%s\n%s\n",
                "1. add task",
                "2. edit task",
                "3. delete task",
                "4. view tasks",
                "5. exit");
    }
}