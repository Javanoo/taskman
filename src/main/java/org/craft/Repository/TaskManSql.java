package org.craft.Repository;

import org.craft.Model.Task;

import java.sql.*;



public abstract class TaskManSql implements TaskManRepo<Task>{
    private DriverManager driverManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public TaskManSql(DriverManager driverManager, String url, String user, String password) throws SQLException {
        setConnection(DriverManager.getConnection(url, user, password));
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public void setDriverManager(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
