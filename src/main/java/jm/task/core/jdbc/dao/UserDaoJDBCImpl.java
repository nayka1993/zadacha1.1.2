package jm.task.core.jdbc.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;
    private Statement statement;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.connectDatabase();
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {

        String sqlCommand = "CREATE TABLE users " +
                "(Id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                "Name VARCHAR(20) NOT NULL, " +
                "lastName VARCHAR(20) NOT NULL, " +
                "Age INTEGER NOT NULL )";
        try {
            statement.executeUpdate(sqlCommand);
        } catch (MySQLSyntaxErrorException ignored) {

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropUsersTable() {

        try {
            statement.executeUpdate("DROP TABLE users");
        } catch (MySQLSyntaxErrorException ignored) {

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlCommand = "INSERT users (name, lastName, Age) Values (?, ?, ? )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {

        String deleteId = "DELETE FROM users WHERE" + id;
        try {
            statement.executeUpdate(deleteId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User(resultSet.getNString("name"),
                        resultSet.getNString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("Id"));
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {

        try {
            statement.executeUpdate("DELETE FROM users;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
