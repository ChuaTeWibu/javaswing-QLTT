package dbconect;

import java.sql.*;

public class DataProvider {
    // Khai báo các hằng số cho thông tin kết nối
    private static final String URL = "jdbc:mysql://localhost:3306/quanlytrungtam";
    private static final String USER = "root";
    private static final String PASSWORD = "tohkaty01";

    // 1. Lấy kết nối đến MySQL
    public static Connection getConnection() throws SQLException {
        try {
            // Đăng ký driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    // 2. Thực thi SELECT với PreparedStatement
    public static ResultSet getData(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
            
            // Set parameters if any
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            closeAll(connection, statement, resultSet);
            ex.printStackTrace();
            return null;
        }
    }

    // 3. Thực thi INSERT, UPDATE, DELETE với PreparedStatement
    public static boolean executeNonQuery(String sql, Object... parameters) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            // Set parameters if any
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Phương thức đóng các tài nguyên
    private static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//	  public static void main(String[] args) {
//	  // Ví dụ thực hiện câu SELECT
//	  System.out.println("----- Kết quả SELECT -----");
//	  String selectQuery = "SELECT * FROM taikhoan"; // Thay 'your_table' bằng bảng của bạn
//	
//	  try {
//	      ResultSet rs = DataProvider.getData(selectQuery);
//	      while (rs != null && rs.next()) {
//	      	System.out.println("MaTK: " + rs.getString("MaTK") + 
//	                  ", MatKhau: " + rs.getString("MatKhau") + 
//	                  ", Quyen: " + rs.getString("Quyen"));
//	      }
//	  } catch (SQLException e) {
//	      e.printStackTrace();
//	  }
//	  }
}