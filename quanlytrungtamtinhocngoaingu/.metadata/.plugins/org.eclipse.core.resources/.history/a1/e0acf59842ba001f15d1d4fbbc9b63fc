package dbconect;

import java.sql.*;

public class DAL_HocVien {
    
    /**
     * Lấy danh sách học viên theo tên
     * @param tenHV Tên học viên cần tìm
     * @return ResultSet chứa danh sách học viên phù hợp
     */
    public ResultSet danhSachHocVien(String tenHV) {
        String query = "SELECT * FROM HocVien WHERE TenHV LIKE ?";
        return DataProvider.getData(query, "%" + tenHV + "%");
    }
    
    /**
     * Xóa học viên khỏi bảng LopHoc_HocVien
     * @param maHV Mã học viên cần xóa
     * @throws SQLException nếu có lỗi xảy ra
     */
    private void xoaHocVienKhoiLopHoc(String maHV, Connection conn) throws SQLException {
        String sql = "DELETE FROM LopHoc_HocVien WHERE MaHV = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, maHV);
        pstmt.executeUpdate();
    }
    
    /**
     * Xóa học viên theo mã
     * @param maHV Mã học viên cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     * @throws SQLException nếu có lỗi xảy ra
     */
    public boolean xoaHocVien(String maHV) throws SQLException {
        Connection conn = null;
        boolean success = false;
        
        try {
            conn = DataProvider.getConnection();
            conn.setAutoCommit(false);

            // Xóa học viên khỏi tất cả các lớp học trước
            xoaHocVienKhoiLopHoc(maHV, conn);

            // Sau đó xóa học viên
            String sql = "DELETE FROM HocVien WHERE MaHV = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maHV);
            pstmt.executeUpdate();

            // Commit transaction
            conn.commit();
            success = true;
            
        } catch (SQLException e) {
            // Rollback trong trường hợp có lỗi
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            // Khôi phục auto-commit và đóng kết nối
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return success;
    }
    
    /**
     * Thêm học viên mới
     * @param maHV Mã học viên
     * @param tenHV Tên học viên
     * @param dienThoai Số điện thoại
     * @param diaChi Địa chỉ
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themHocVien(String maHV, String tenHV, String dienThoai, String diaChi) {
        String query = "INSERT INTO HocVien(MaHV, TenHV, DienThoai, DiaChi) VALUES (?, ?, ?, ?)";
        return DataProvider.executeNonQuery(query, maHV, tenHV, dienThoai, diaChi);
    }
    
    /**
     * Cập nhật thông tin học viên
     * @param maHV Mã học viên
     * @param tenHV Tên học viên mới
     * @param dienThoai Số điện thoại mới
     * @param diaChi Địa chỉ mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    public boolean suaHocVien(String maHV, String tenHV, String dienThoai, String diaChi) {
        String query = "UPDATE HocVien SET TenHV = ?, DienThoai = ?, DiaChi = ? WHERE MaHV = ?";
        return DataProvider.executeNonQuery(query, tenHV, dienThoai, diaChi, maHV);
    }
}