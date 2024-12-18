package dbconect;

import java.sql.*;

/**
 * Lớp xử lý danh sách đăng ký môn học
 */
public class DAL_Dsdk {
    
    /**
     * Lấy danh sách đăng ký môn học của học viên
     */
    public ResultSet danhSachDangKy(String tenHV, String maHV) {
        String query = "SELECT c.MaHV, d.TenHV, b.MaMH, a.TenMH " +
                      "FROM MonHoc a " +
                      "LEFT JOIN LopHoc b ON a.MaMH = b.MaMH " +
                      "LEFT JOIN LopHoc_HocVien c ON b.MaLH = c.MaLH " +
                      "LEFT JOIN HocVien d ON c.MaHV = d.MaHV " +
                      "WHERE d.TenHV LIKE ? AND c.MaHV LIKE ?";
        try {
            return DataProvider.getData(query, "%" + tenHV + "%", "%" + maHV + "%");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Phương thức test chức năng
     */
//    public static void main(String[] args) {
//        DAL_Dsdk dal = new DAL_Dsdk();
//        
//        // Test tìm kiếm danh sách đăng ký
//        System.out.println("=== Test tìm kiếm danh sách đăng ký ===");
//        
//        try {
//            ResultSet rs = dal.danhSachDangKy("", "");
//            
//            if (rs != null) {
//                while (rs.next()) {
//                    System.out.println("Thông tin đăng ký:");
//                    System.out.println("Mã học viên: " + rs.getString("MaHV"));
//                    System.out.println("Tên học viên: " + rs.getString("TenHV"));
//                    System.out.println("Mã môn học: " + rs.getString("MaMH"));
//                    System.out.println("Tên môn học: " + rs.getString("TenMH"));
//                }
//            } else {
//                System.out.println("Không tìm thấy thông tin đăng ký!");
//            }
//            
//        } catch (SQLException e) {
//            System.out.println("Lỗi SQL: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}