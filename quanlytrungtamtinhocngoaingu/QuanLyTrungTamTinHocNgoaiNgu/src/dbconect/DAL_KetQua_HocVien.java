package dbconect;

import java.sql.*;

/**
 * Lớp xử lý kết quả học tập của học viên
 */
public class DAL_KetQua_HocVien {
    
    /**
     * Lấy danh sách kết quả học tập
     */
    public ResultSet danhSachKetQua(String maLH, String maHV) {
        String query = "SELECT MaLH, MaHV, Diem " +
                      "FROM LopHoc_HocVien " +
                      "WHERE MaLH LIKE ? AND MaHV LIKE ? " +
                      "ORDER BY MaLH";
        try {
            return DataProvider.getData(query, "%" + maLH + "%", "%" + maHV + "%");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Nhập điểm cho học viên
     */
    public boolean nhapDiem(String maLH, String maHV, String diem) {
        String query = "UPDATE LopHoc_HocVien " +
                      "SET Diem = ? " +
                      "WHERE MaLH = ? AND MaHV = ?";
        return DataProvider.executeNonQuery(query, diem, maLH, maHV);
    }

    /**
     * Phương thức test chức năng
     */
//    public static void main(String[] args) {
//        DAL_KetQua_HocVien dal = new DAL_KetQua_HocVien();
//        
//        // Test xem kết quả
//        System.out.println("=== Test xem kết quả học tập ===");
//        try {
//            ResultSet rs = dal.danhSachKetQua("", "");
//            
//            if (rs != null) {
//                while (rs.next()) {
//                    System.out.println("Thông tin kết quả:");
//                    System.out.println("Mã lớp học: " + rs.getString("MaLH"));
//                    System.out.println("Mã học viên: " + rs.getString("MaHV")); 
//                    System.out.println("Điểm: " + rs.getString("Diem"));
//                }
//            } else {
//                System.out.println("Không tìm thấy kết quả!");
//            }
//            
//        } catch (SQLException e) {
//            System.out.println("Lỗi SQL: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//       
//    }
}