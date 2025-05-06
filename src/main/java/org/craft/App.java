package org.craft;

import org.craft.Service.TaskManService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    TaskManService service = new TaskManService();

    public App(){
     service.start();
    }
}