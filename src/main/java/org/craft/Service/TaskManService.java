package org.craft.Service;

import org.craft.Model.Task;
import org.craft.Repository.TaskRepo;

public class TaskManService {
    TaskRepo taskRepo = new TaskRepo();

    public void addTask() {
        taskRepo.add(createTask());
    }

    private Task createTask() {
        return null;
    }

    public void editTask() {
        taskRepo.update(updateTask());
    }

    private Task updateTask() {
        return null;
    }

    public void deleteTask() {
        taskRepo.delete(removeTask());
    }

    private Task removeTask() {
        return null;
    }

    public void viewTasks() {
        taskRepo.get();
    }
}
