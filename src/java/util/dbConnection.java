package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    
    private static final String URL = "jdbc:mysql://localhost:3306/PTTK";
    private static final String USER = "root";
    private static final String PASSWORD = "0353205801@Hien";

   
    public static Connection getConnection() {
        Connection conn = null;
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối CSDL thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy Driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối CSDL: " + e.getMessage());
        }
        return conn;
    }

    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Đã đóng kết nối!");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
