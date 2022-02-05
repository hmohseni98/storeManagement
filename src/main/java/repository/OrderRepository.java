package repository;

import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.MyConnection;

public class OrderRepository implements BaseRepository<Order> {
    Connection connection = MyConnection.connection;

    @Override
    public int save(Order order) {
        Integer id = null;
        try {
            String save = "INSERT INTO \"order\" (product_id, customer_id, shopping_card_id) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getProductId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getShoppingCardId());
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
    public void update(Order order) {
        try {
            String update = "UPDATE \"order\" " +
                    "SET product_id = ? , customer_id = ? , shopping_card_id = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, order.getProductId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setInt(3, order.getShoppingCardId());
            preparedStatement.setInt(4, order.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        try {
            String findAll = "SELECT * FROM \"order\" ";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderList.add(new Order(resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("shopping_card_id")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return orderList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM \"order\" WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public Order findById(int id) {
        Order order = null;
        try {
            String findById = "SELECT * FROM \"order\" WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = new Order(resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("shopping_card_id"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return order;
    }
}
