package org.craft;

import org.craft.Service.TaskManService;

public class App {
    TaskManService service = new TaskManService();
    public App(){
     service.start();
    }
}