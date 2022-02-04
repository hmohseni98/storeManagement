package repository;

import entity.Admin;
import Database.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminRepository implements UserInterface<Admin> {
    Connection connection = MyConnection.connection;

    @Override
    public int save(Admin admin) {
        try {
            String save = "INSERT INTO admin (username, password, national_code) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save);
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return 1;
    }

    @Override
    public void update(Admin admin) {
        try {
            String update = "UPDATE admin\n" +
                    "set username = ? , password = ? , national_code = ?\n" +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Admin findById(int id) {
        return null;
    }

    @Override
    public Admin login(String username, String password) {
        return null;
    }
}
