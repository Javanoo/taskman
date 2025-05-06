package org.craft.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public interface TaskManRepo<Task> {

    void addTasks(Task task) throws SQLException;

    void editTask(Task task) throws SQLException;

    HashSet<Task> getTasks() throws SQLException;

    void deleteTask(Task task) throws SQLException;

    void deleteTasks(ArrayList<Task> tasks) throws SQLException;
}