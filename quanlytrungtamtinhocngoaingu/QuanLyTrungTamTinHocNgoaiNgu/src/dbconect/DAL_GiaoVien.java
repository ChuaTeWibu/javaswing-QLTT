package dbconect;

import java.sql.*;

/**
 * Lớp xử lý các thao tác với giáo viên trong database
 * Bao gồm các chức năng: tìm kiếm, thêm, sửa, xóa giáo viên
 */
public class DAL_GiaoVien {
    
    /**
     * Lấy danh sách giáo viên theo tên
     * @param tenGV Tên giáo viên cần tìm (có thể là một phần của tên)
     * @return ResultSet chứa danh sách giáo viên phù hợp, null nếu có lỗi
     */
    public ResultSet danhSachGiaoVien(String tenGV) {
        // Câu truy vấn SQL với LIKE để tìm kiếm tương đối
        String query = "SELECT * FROM GiaoVien WHERE TenGV LIKE ?";
        try {
            // Gọi phương thức getData từ DataProvider và truyền tham số
            ResultSet rs = DataProvider.getData(query, "%" + tenGV + "%");
            if (rs != null) {
                return rs;
            }
        } catch (Exception e) {
            // In ra lỗi nếu có
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Thêm giáo viên mới vào database
     * @param maGV Mã giáo viên (khóa chính)
     * @param tenGV Tên giáo viên
     * @param gioiTinh Giới tính
     * @param ngaySinh Ngày sinh (định dạng yyyy-MM-dd)
     * @param trinhDo Trình độ học vấn
     * @param khoa Khoa/Bộ môn
     * @param ghiChu Ghi chú thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themGiaoVien(String maGV, String tenGV, String gioiTinh, 
            String ngaySinh, String trinhDo, String khoa, String ghiChu) {
        // Câu lệnh SQL INSERT
        String query = "INSERT INTO GiaoVien(MaGV, TenGV, GioiTinh, NgaySinh, TrinhDo, Khoa, GhiChu) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Thực thi câu lệnh và trả về kết quả
        return DataProvider.executeNonQuery(query, maGV, tenGV, gioiTinh, 
                ngaySinh, trinhDo, khoa, ghiChu);
    }
    
    /**
     * Cập nhật thông tin giáo viên
     * @param maGV Mã giáo viên cần sửa
     * @param tenGV Tên giáo viên mới
     * @param gioiTinh Giới tính mới
     * @param ngaySinh Ngày sinh mới
     * @param trinhDo Trình độ mới
     * @param khoa Khoa mới
     * @param ghiChu Ghi chú mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean suaGiaoVien(String maGV, String tenGV, String gioiTinh, 
            String ngaySinh, String trinhDo, String khoa, String ghiChu) {
        // Câu lệnh SQL UPDATE
        String query = "UPDATE GiaoVien SET TenGV = ?, GioiTinh = ?, NgaySinh = ?, " +
                      "TrinhDo = ?, Khoa = ?, GhiChu = ? WHERE MaGV = ?";
        // Thực thi câu lệnh và trả về kết quả
        return DataProvider.executeNonQuery(query, tenGV, gioiTinh, ngaySinh, 
                trinhDo, khoa, ghiChu, maGV);
    }
    
    /**
     * Xóa giáo viên khỏi database
     * @param maGV Mã giáo viên cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaGiaoVien(String maGV) {
        // Câu lệnh SQL DELETE
        String query = "DELETE FROM GiaoVien WHERE MaGV = ?";
        // Thực thi câu lệnh và trả về kết quả
        return DataProvider.executeNonQuery(query, maGV);
    }

    /**
     * Phương thức main để test các chức năng
     */
//    public static void main(String[] args) {
//        // Tạo đối tượng để test
//        DAL_GiaoVien dal = new DAL_GiaoVien();
//        
//        // Test tìm kiếm giáo viên
//        System.out.println("=== Test tìm kiếm giáo viên ===");
//        try {
//            // Từ khóa tìm kiếm (để trống để lấy tất cả)
//            String tuKhoa = "";
//            System.out.println("Tìm kiếm với từ khóa: '" + tuKhoa + "'");
//            
//            // Thực hiện tìm kiếm
//            ResultSet rs = dal.danhSachGiaoVien(tuKhoa);
//            
//            // Kiểm tra và hiển thị kết quả
//            if (rs != null) {
//                boolean coKetQua = false;
//                
//                while (rs.next()) {
//                    coKetQua = true;
//                    // In thông tin từng giáo viên
//                    System.out.println("\nThông tin giáo viên:");
//                    System.out.println("MaGV: " + rs.getString("MaGV"));
//                    System.out.println("TenGV: " + rs.getString("TenGV"));
//                    System.out.println("GioiTinh: " + rs.getString("GioiTinh"));
//                    System.out.println("NgaySinh: " + rs.getString("NgaySinh"));
//                    System.out.println("TrinhDo: " + rs.getString("TrinhDo"));
//                    System.out.println("Khoa: " + rs.getString("Khoa"));
//                    System.out.println("GhiChu: " + rs.getString("GhiChu"));
//                    System.out.println("---------------");
//                }
//                
//                // Thông báo nếu không tìm thấy kết quả
//                if (!coKetQua) {
//                    System.out.println("Không tìm thấy giáo viên nào!");
//                }
//            } else {
//                System.out.println("Lỗi khi truy vấn dữ liệu!");
//            }
//            
//        } catch (SQLException e) {
//            // Xử lý ngoại lệ SQL
//            System.out.println("Lỗi SQL: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}