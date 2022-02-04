package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection;
    static {
        try {
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","123456");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
