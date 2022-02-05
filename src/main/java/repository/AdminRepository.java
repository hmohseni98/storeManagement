package repository;

import entity.Admin;
import database.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository implements UserInterface<Admin> {
    private Connection connection = MyConnection.connection;

    @Override
    public int save(Admin admin) {
        Integer id = null;
        try {
            String save = "INSERT INTO admin (username, password, national_code) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getNationalCode());
            preparedStatement.execute();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                id = generatedKey.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return id;
    }

    @Override
    public void update(Admin admin) {
        try {
            String update = "UPDATE admin " +
                    "set username = ? , password = ? , national_code = ? " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getNationalCode());
            preparedStatement.setInt(4, admin.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        try {
            String findAll = "SELECT * FROM admin";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adminList.add(new Admin(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("national_code")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return adminList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM admin WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public Admin findById(int id) {
        Admin admin = null;
        try {
            String findById = "SELECT * FROM admin WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin = new Admin(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("national_code"));
            }
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return admin;
    }

    @Override
    public Admin login(String username, String password) {
        Admin admin = null;
        try {
            String login ="SELECT * FROM admin WHERE username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(login);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                admin = new Admin(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("national_code"));
            }
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return admin;
    }
}
