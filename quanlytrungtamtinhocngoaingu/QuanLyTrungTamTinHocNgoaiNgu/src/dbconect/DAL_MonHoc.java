package dbconect;

import java.sql.*;

/**
 * Lớp xử lý các thao tác với môn học trong database
 * Bao gồm các chức năng: tìm kiếm, thêm, sửa, xóa môn học
 */
public class DAL_MonHoc {
    
    /**
     * Lấy danh sách môn học theo tên
     * @param tenMH Tên môn học cần tìm
     * @return ResultSet chứa danh sách môn học phù hợp
     */
    public ResultSet danhSachMonHoc(String tenMH) {
        // Câu truy vấn SQL với LIKE để tìm kiếm tương đối
        String query = "SELECT * FROM MonHoc WHERE TenMH LIKE ?";
        try {
            ResultSet rs = DataProvider.getData(query, "%" + tenMH + "%");
            if (rs != null) {
                return rs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Thêm môn học mới
     * @param maMH Mã môn học
     * @param tenMH Tên môn học
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themMonHoc(String maMH, String tenMH) {
        String query = "INSERT INTO MonHoc(MaMH, TenMH) VALUES (?, ?)";
        return DataProvider.executeNonQuery(query, maMH, tenMH);
    }
    
    /**
     * Cập nhật thông tin môn học
     * @param maMH Mã môn học cần sửa
     * @param tenMH Tên môn học mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean suaMonHoc(String maMH, String tenMH) {
        String query = "UPDATE MonHoc SET TenMH = ? WHERE MaMH = ?";
        return DataProvider.executeNonQuery(query, tenMH, maMH);
    }
    
    /**
     * Xóa môn học
     * @param maMH Mã môn học cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaMonHoc(String maMH) {
        String query = "DELETE FROM MonHoc WHERE MaMH = ?";
        return DataProvider.executeNonQuery(query, maMH);
    }

    /**
     * Phương thức main để test các chức năng
     */
//    public static void main(String[] args) {
//        // Tạo đối tượng để test
//        DAL_MonHoc dal = new DAL_MonHoc();
//        
//        // Test tìm kiếm môn học
//        System.out.println("=== Test tìm kiếm môn học ===");
//        try {
//            String tuKhoa = ""; // Để trống để lấy tất cả môn học
//            System.out.println("Tìm kiếm với từ khóa: '" + tuKhoa + "'");
//            
//            ResultSet rs = dal.danhSachMonHoc(tuKhoa);
//            
//            if (rs != null) {
//                boolean coKetQua = false;
//                
//                while (rs.next()) {
//                    coKetQua = true;
//                    System.out.println("\nThông tin môn học:");
//                    System.out.println("MaMH: " + rs.getString("MaMH"));
//                    System.out.println("TenMH: " + rs.getString("TenMH"));
//                    System.out.println("---------------");
//                }
//                
//                if (!coKetQua) {
//                    System.out.println("Không tìm thấy môn học nào!");
//                }
//            } else {
//                System.out.println("Lỗi khi truy vấn dữ liệu!");
//            }
//            
//        } catch (SQLException e) {
//            System.out.println("Lỗi SQL: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}