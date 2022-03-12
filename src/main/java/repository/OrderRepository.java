package repository;

import customException.RecordDoesNotExist;
import entity.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.MyConnection;
import entity.ShoppingCard;

public class OrderRepository implements BaseRepository<Orders> {
    Connection connection = MyConnection.connection;

    @Override
    public int save(Orders orders) {
        Integer id = null;
        try {
            String save = "INSERT INTO orders (productid, customerid, shoppingcard_id) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, orders.getProductId());
            preparedStatement.setInt(2, orders.getCustomerId());
            preparedStatement.setInt(3, orders.getShoppingCard().getId());
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
    public void update(Orders orders) {
        try {
            String update = "UPDATE orders " +
                    "SET productid = ? , customerid = ? , shoppingcard_id = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, orders.getProductId());
            preparedStatement.setInt(2, orders.getCustomerId());
            preparedStatement.setInt(3, orders.getShoppingCard().getId());
            preparedStatement.setInt(4, orders.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Orders> findAll() {
        List<Orders> OrdersList = new ArrayList<>();
        try {
            String findAll = "SELECT o.*,s.id as sid,s.date,s.payed FROM orders o " +
                    "INNER JOIN shoppingcard s " +
                    "ON o.shoppingcard_id = s.id";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrdersList.add(new Orders(resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        new ShoppingCard(resultSet.getInt("sid"),
                                resultSet.getDate("date"),
                                resultSet.getBoolean("payed"))));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return OrdersList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM orders WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            if (preparedStatement.execute())
                throw new RecordDoesNotExist();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public Orders findById(int id) {
        Orders Orders = null;
        try {
            String findById = "SELECT o.*,s.id as sid,s.date,s.payed FROM orders o " +
                    "INNER JOIN shoppingcard s " +
                    "ON o.shoppingcard_id = s.id " +
                    "WHERE o.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Orders = new Orders(resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        new ShoppingCard(resultSet.getInt("sid"),
                                resultSet.getDate("date"),
                                resultSet.getBoolean("payed")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return Orders;
    }
}
