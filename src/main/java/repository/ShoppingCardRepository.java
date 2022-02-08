package repository;

import customException.RecordDoesNotExist;
import entity.ShoppingCard;
import database.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCardRepository implements BaseRepository<ShoppingCard> {
    private Connection connection = MyConnection.connection;

    @Override
    public int save(ShoppingCard shoppingCard) {
        Integer id = null;
        try {
            String save = "INSERT INTO shopping_card (date, payed) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, shoppingCard.getDate());
            preparedStatement.setBoolean(2, shoppingCard.isPayed());
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
    public void update(ShoppingCard shoppingCard) {
        try {
            String update = "UPDATE shopping_card " +
                    "SET date = ? , payed = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setDate(1, shoppingCard.getDate());
            preparedStatement.setBoolean(2, shoppingCard.isPayed());
            preparedStatement.setInt(3, shoppingCard.getId());
            if (!preparedStatement.execute())
                throw new RecordDoesNotExist();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<ShoppingCard> findAll() {
        List<ShoppingCard> shoppingCardList = new ArrayList<>();
        try {
            String findAll = "SELECT * FROM shopping_card";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shoppingCardList.add(new ShoppingCard(resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getBoolean("payed")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return shoppingCardList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM shopping_card WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public ShoppingCard findById(int id) {
        ShoppingCard shoppingCard = null;
        try {
            String findAll = "SELECT * FROM shopping_card WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shoppingCard = new ShoppingCard(resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getBoolean("payed"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return shoppingCard;
    }

}
