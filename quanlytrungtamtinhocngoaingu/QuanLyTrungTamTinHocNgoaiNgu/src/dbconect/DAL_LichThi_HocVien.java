package dbconect;

import java.sql.*;

/**
 * Lớp xử lý lịch thi của học viên
 */
public class DAL_LichThi_HocVien {
    
    /**
     * Lấy danh sách lịch thi của học viên
     */
    public ResultSet danhSachLichThi(String maLH, String maHV) {
        String query = "SELECT MaLH, MaHV, NgayThi, CaThi " +
                      "FROM LopHoc_HocVien " +
                      "WHERE MaLH LIKE ? AND MaHV LIKE ?";
        try {
            return DataProvider.getData(query, "%" + maLH + "%", "%" + maHV + "%");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Cập nhật lịch thi cho học viên
     */
    public boolean suaLichThi(String maLH, String ngayThi, String caThi) {
        String query = "UPDATE LopHoc_HocVien " +
                      "SET NgayThi = ?, CaThi = ? " +
                      "WHERE MaLH = ?";
        return DataProvider.executeNonQuery(query, ngayThi, caThi, maLH);
    }
    
    /**
     * Xóa lịch thi của học viên
     */
    public boolean xoaLichThi(String maLH) {
        String query = "UPDATE LopHoc_HocVien " +
                      "SET NgayThi = NULL, CaThi = '' " +
                      "WHERE MaLH = ?";
        return DataProvider.executeNonQuery(query, maLH);
    }

    /**
     * Phương thức test chức năng
     */
//    public static void main(String[] args) {
//        DAL_LichThi_HocVien dal = new DAL_LichThi_HocVien();
//        
//        // Test tìm kiếm lịch thi
//        System.out.println("=== Test tìm kiếm lịch thi ===");
//        try {
//            ResultSet rs = dal.danhSachLichThi("", "");
//            
//            if (rs != null) {
//                while (rs.next()) {
//                    System.out.println("Thông tin lịch thi:");
//                    System.out.println("Mã lớp học: " + rs.getString("MaLH"));
//                    System.out.println("Mã học viên: " + rs.getString("MaHV"));
//                    System.out.println("Ngày thi: " + rs.getString("NgayThi"));
//                    System.out.println("Ca thi: " + rs.getString("CaThi"));
//                }
//            } else {
//                System.out.println("Không tìm thấy lịch thi!");
//            }
//            
//        } catch (SQLException e) {
//            System.out.println("Lỗi SQL: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}