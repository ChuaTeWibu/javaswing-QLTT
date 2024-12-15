package dbconect;

import java.sql.*;

public class DAL_TaiKhoan {
    
    // Phương thức kiểm tra đăng nhập
    // Tham số: maTK - tên đăng nhập, matKhau - mật khẩu, quyen - để lưu quyền của tài khoản
    // Trả về: ResultSet chứa thông tin tài khoản nếu đăng nhập thành công
    public ResultSet kiemTraDangNhap(String maTK, String matKhau, StringBuilder quyen) {
        // Câu truy vấn SQL với tham số ? để tránh SQL injection
        String query = "SELECT * FROM TaiKhoan WHERE MaTK = ? AND MatKhau = ?";
        
        try {
            // Gọi phương thức getData từ DataProvider để thực hiện truy vấn
            // Truyền vào câu query và các tham số maTK, matKhau
            ResultSet result = DataProvider.getData(query, maTK, matKhau);
            
            // Kiểm tra kết quả trả về
            if (result != null && result.next()) {
                // Nếu tìm thấy tài khoản, lưu quyền vào StringBuilder
                quyen.append(result.getString("Quyen"));
                // Di chuyển con trỏ về đầu ResultSet để đọc lại từ đầu
                result.beforeFirst();
            } else {
                // Nếu không tìm thấy, xóa nội dung của StringBuilder
                quyen.setLength(0);
            }
            
            return result;
            
        } catch (SQLException e) {
            // Xử lý ngoại lệ SQL
            e.printStackTrace();
            // Xóa nội dung của StringBuilder trong trường hợp lỗi
            quyen.setLength(0);
            return null;
        }
    }


}