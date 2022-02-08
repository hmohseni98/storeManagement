package repository;

import entity.Admin;
import entity.Customer;
import entity.Order;
import entity.ShoppingCard;
import database.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements CustomerInterface {
    Connection connection = MyConnection.connection;

    @Override
    public int save(Customer customer) {
        Integer id = null;
        try {
            String save = "INSERT INTO customer (username, password, address) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getAddress());
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
    public void update(Customer customer) {
        try {
            String update = "UPDATE customer " +
                    "set username = ? , password = ? , address = ? " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        try {
            String findAll = "SELECT * FROM customer";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customerList.add(new Customer(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("address")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return customerList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM customer WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public Customer findById(int id) {
        Customer customer = null;
        try {
            String findById = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("address"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return customer;
    }

    @Override
    public List<Order> findOrderByUserId(int id) {
        List<Order> orderList = new ArrayList<>();
        try{
            String findShoppingCardByUserId = "SELECT o.*,s.id as sid,s.date,s.payed FROM \"order\" o " +
                    "INNER JOIN shopping_card s " +
                    "ON o.shopping_card_id = s.id " +
                    "WHERE o.customer_id = ? AND s.payed = false ";
            PreparedStatement preparedStatement = connection.prepareStatement(findShoppingCardByUserId);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                orderList.add(new Order(resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        new ShoppingCard(resultSet.getInt("sid"),
                                resultSet.getDate("date"),
                                resultSet.getBoolean("payed"))));
            }
            preparedStatement.close();
        }catch (SQLException e){
            System.out.println("database error");
        }
        return orderList;
    }

    @Override
    public Customer login(String username, String password) {
        Customer customer = null;
        try {
            String login = "SELECT * FROM customer WHERE username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(login);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return customer;
    }
}
