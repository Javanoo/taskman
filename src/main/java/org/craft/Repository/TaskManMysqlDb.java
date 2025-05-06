package org.craft.Repository;


import org.craft.Model.Task;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;

public class TaskManMysqlDb extends TaskManSql {
    private LinkedHashSet<Task> tasks;

   public TaskManMysqlDb (DriverManager driverManager, String url, String user, String password) throws Exception{
        super(driverManager, url, user, password);
        setTasks(new LinkedHashSet<>());
   }

    @Override
    public void addTasks(Task task) throws SQLException {
       String sql = " INSERT INTO Tasks SET title = ?, " +
               " priority = ?, done = ?, createdOn = ?";
           initialisePreparedStatement(sql,task);
           //add to database
           getPreparedStatement().executeUpdate();
    }

    @Override
    public void editTask(Task task) throws SQLException {
        String sql = " UPDATE Tasks SET title = ?, " +
                " priority = ?, done = ?, createdOn = ? WHERE id = ?";
        initialisePreparedStatement(sql, task);
        getPreparedStatement().setInt(5, task.getId());
        getPreparedStatement().executeUpdate();
    }

    @Override
    public LinkedHashSet<Task> getTasks() throws SQLException {
       final String sql = "SELECT * FROM Tasks";
        setPreparedStatement(getConnection().prepareStatement(sql));
        setResultSet(getPreparedStatement().executeQuery());
        while(getResultSet().next()){
            Task task = new Task();
            task.setId(getResultSet().getInt(1));
            task.setTitle(getResultSet().getString(2));
            task.setPriority(getResultSet().getInt(3));
            task.setDone(getResultSet().getBoolean(4));
            task.setCreatedOn(new Date(getResultSet().getLong(5)));

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public void deleteTask(Task task) throws SQLException {
        String sql = " DELETE FROM Tasks WHERE id = ?";
        setPreparedStatement(getConnection().prepareStatement(sql));
        getPreparedStatement().setInt(1, task.getId());
        getPreparedStatement().executeUpdate();
    }

    @Override
    public void deleteTasks(ArrayList<Task> tasks) throws SQLException {
       for(Task task : tasks){
           deleteTask(task);
       }
    }

    private void initialisePreparedStatement(String sql, Task task) throws SQLException {
        setPreparedStatement(getConnection().prepareStatement(sql));
        getPreparedStatement().setString(1,task.getTitle());
        getPreparedStatement().setInt(2,task.getPriority());
        getPreparedStatement().setBoolean(3, task.isDone());
        getPreparedStatement().setLong(4,task.getCreatedOn().getTime());
    }

    public void setTasks(LinkedHashSet<Task> tasks) {
        this.tasks = tasks;
    }
}