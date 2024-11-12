package lk.ijse.gdse71.finalproject.jotit.util;

import lk.ijse.gdse71.finalproject.jotit.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    private static Connection connection;

    public static void beginTransaction() throws SQLException, ClassNotFoundException {
        connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException {
        if (connection != null) {
            connection.commit();
            connection.setAutoCommit(true);
            connection = null;
        }
    }

    public static void rollbackTransaction() throws SQLException {
        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
            connection = null;
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static <T> T execute(String sql, Object... obj) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = DBConnection.getInstance().getConnection();
        }
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject((i + 1), obj[i]);
        }

        if (sql.toLowerCase().startsWith("select")) {
            return (T) pst.executeQuery();
        } else {
            return (T) ((Boolean) (pst.executeUpdate() > 0));
        }
    }
}