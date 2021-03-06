package repository;

import entity.Category;
import entity.Product;
import database.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements ProductInterface {
    private Connection connection = MyConnection.connection;


    @Override
    public int save(Product product) {
        Integer id = null;
        try {
            String save = "INSERT INTO product (name, description, category_id, qty, price) VALUES  (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getCategory().getId());
            preparedStatement.setInt(4, product.getQty());
            preparedStatement.setInt(5, product.getPrice());
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
    public void update(Product product) {
        try {
            String update = "UPDATE product " +
                    "SET name = ? , description = ? , category_id = ? , qty = ? , price = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getCategory().getId());
            preparedStatement.setInt(4, product.getQty());
            preparedStatement.setInt(5, product.getPrice());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        try {
            String findAll = "SELECT product.*,c.id as cid,c.title,c.description as cdescription,c.category_id" +
                    " as ccategory_id FROM product " +
                    "INNER JOIN category c " +
                    "on c.id = product.category_id";
            PreparedStatement preparedStatement = connection.prepareStatement(findAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        new Category(resultSet.getInt("cid"),
                                resultSet.getString("title"),
                                resultSet.getString("cdescription"),
                                resultSet.getInt("ccategory_id")),
                        resultSet.getInt("qty"),
                        resultSet.getInt("price")));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return productList;
    }

    @Override
    public void delete(int id) {
        try {
            String delete = "DELETE FROM product WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try {
            String findById = "SELECT product.*,c.id as cid,c.title,c.description as cdescription,c.category_id" +
                    " as ccategory_id FROM product " +
                    "INNER JOIN category c " +
                    "ON c.id = product.category_id " +
                    "WHERE product.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        new Category(resultSet.getInt("cid"),
                                resultSet.getString("title"),
                                resultSet.getString("cdescription"),
                                resultSet.getInt("ccategory_id")),
                        resultSet.getInt("qty"),
                        resultSet.getInt("price"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return product;
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        List<Product> productList = new ArrayList<>();
        try {
            String findByCategory = "SELECT product.*,c.id as cid,c.title,c.description as cdescription,c.category_id " +
                    " as ccategory_id FROM product " +
                    " INNER JOIN category c " +
                    " on c.id = product.category_id" +
                    " WHERE c.category_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findByCategory);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        new Category(resultSet.getInt("cid"),
                                resultSet.getString("title"),
                                resultSet.getString("cdescription"),
                                resultSet.getInt("ccategory_id")),
                        resultSet.getInt("qty"),
                        resultSet.getInt("price")));
            }

        } catch (SQLException e) {
            System.out.println("database error");
        }
        return productList;
    }

    @Override
    public void updateQty(Product product) {
        try {
            String updateQty = " UPDATE product " +
                    "SET qty = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQty);
            preparedStatement.setInt(1, product.getQty() - 1);
            preparedStatement.setInt(2, product.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
    }

    @Override
    public int findProductIdByShoppingCardId(int shoppingCardId) {
        Integer productId = null;
        try {
            String updateQty = "SELECT p.id FROM shopping_card " +
                    "INNER JOIN \"order\" o ON " +
                    "    shopping_card.id = o.shopping_card_id " +
                    "INNER JOIN product p ON " +
                    "    p.id = o.product_id " +
                    "WHERE shopping_card_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQty);
            preparedStatement.setInt(1, shoppingCardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                productId = resultSet.getInt("id");
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("database error");
        }
        return productId;
    }
}
