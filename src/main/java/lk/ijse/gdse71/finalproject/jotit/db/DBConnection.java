package lk.ijse.gdse71.finalproject.jotit.db;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
    public class DBConnection {
        private static DBConnection dbConnection;
        private Connection connection;
        private DBConnection() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jottie",
                    "root",
                    "Viraj@2002"
            );
        }
        public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
            if (dbConnection==null){
                dbConnection=new DBConnection();
            }
            return dbConnection;
        }
    }

