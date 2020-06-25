package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Илья", "Алкоголик", (byte) 21);
        userService.saveUser("Егор", "Сидоров", (byte) 25);
        userService.saveUser("Игорь", "Бывалый", (byte) 19);
        userService.saveUser("Антон", "Бабкин", (byte) 16);
        List<User> listUsers = userService.getAllUsers();
        for(User u : listUsers)
            System.out.println(u);
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
