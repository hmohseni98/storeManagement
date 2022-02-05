package repository;

import entity.Admin;
import entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.MyConnection;

public class CategoryRepository implements BaseRepository<Category> {
    private Connection connection = MyConnection.connection;


    @Override
    public int save(Category category) {
        Integer id = null;
        try {
            String save = "INSERT INTO category (title, description, category_id) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getCategoryId());
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
    public void update(Category category) {
        try {
            String update = "UPDATE category " +
                    "set title = ? , description = ? , category_id = ? " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getCategoryId());
            preparedStatement.setInt(4, category.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> categoryList = new ArrayList<>();
        try {
            String findAll = "SELECT * FROM category ";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryList.add(new Category(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("category_id")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return categoryList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM category WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public Category findById(int id) {
        Category category = null;
        try {
            String findById = "SELECT * FROM category WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                category = new Category(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("category_id"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return category;
    }
}
