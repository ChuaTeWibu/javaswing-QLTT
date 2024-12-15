package dbconect;

import java.sql.*;
public class DAL_LopHoc {
    
    public ResultSet getDanhSachLopHoc(String tenLop) throws SQLException {
        String sql = "SELECT l.MaLH, m.TenMH, g.TenGV, l.NgayBatDau, l.NgayKetThuc, l.SoBuoi " +
                    "FROM LopHoc l " +
                    "INNER JOIN MonHoc m ON l.MaMH = m.MaMH " +
                    "INNER JOIN GiaoVien g ON l.MaGV = g.MaGV " +
                    "WHERE l.MaLH LIKE ?";
        return DataProvider.getData(sql, "%" + tenLop + "%");
    }

    public ResultSet getDanhSachGiaoVien() throws SQLException {
        String sql = "SELECT MaGV, TenGV FROM GiaoVien";
        return DataProvider.getData(sql);
    }

    public ResultSet getDanhSachMonHoc() throws SQLException {
        String sql = "SELECT MaMH, TenMH FROM MonHoc";
        return DataProvider.getData(sql);
    }

    public ResultSet getDanhSachHocVien() throws SQLException {
        String sql = "SELECT MaHV, TenHV FROM HocVien";
        return DataProvider.getData(sql);
    }

    public ResultSet getDanhSachHocVienTrongLop(String maLH) throws SQLException {
        String sql = "SELECT h.MaHV, h.TenHV, h.DienThoai, h.DiaChi " +
                    "FROM HocVien h " +
                    "INNER JOIN LopHoc_HocVien lh ON h.MaHV = lh.MaHV " +
                    "WHERE lh.MaLH = ?";
        return DataProvider.getData(sql, maLH);
    }

    public boolean themLopHoc(String maLH, String maMH, String maGV, 
            java.util.Date ngayBD, java.util.Date ngayKT, int soBuoi) throws SQLException {
        String sql = "INSERT INTO LopHoc(MaLH, MaMH, MaGV, NgayBatDau, NgayKetThuc, SoBuoi) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        // Chuyển đổi java.util.Date thành java.sql.Timestamp
        Timestamp ngayBDSQL = new Timestamp(ngayBD.getTime());
        Timestamp ngayKTSQL = new Timestamp(ngayKT.getTime());
        return DataProvider.executeNonQuery(sql, maLH, maMH, maGV, ngayBDSQL, ngayKTSQL, soBuoi);
    }

    public boolean suaLopHoc(String maLH, String maMH, String maGV,
            java.util.Date ngayBD, java.util.Date ngayKT, int soBuoi) throws SQLException {
        String sql = "UPDATE LopHoc SET MaMH = ?, MaGV = ?, NgayBatDau = ?, " +
                    "NgayKetThuc = ?, SoBuoi = ? WHERE MaLH = ?";
        // Chuyển đổi java.util.Date thành java.sql.Timestamp
        Timestamp ngayBDSQL = new Timestamp(ngayBD.getTime());
        Timestamp ngayKTSQL = new Timestamp(ngayKT.getTime());
        return DataProvider.executeNonQuery(sql, maMH, maGV, ngayBDSQL, ngayKTSQL, soBuoi, maLH);
    }

    public boolean themHocVienVaoLop(String maLH, String maHV) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM LopHoc_HocVien WHERE MaLH = ? AND MaHV = ?";
        ResultSet rs = DataProvider.getData(sqlCheck, maLH, maHV);
        if (rs.next() && rs.getInt(1) > 0) {
            throw new SQLException("Học viên đã có trong lớp này!");
        }
        
        String sql = "INSERT INTO LopHoc_HocVien(MaLH, MaHV) VALUES (?, ?)";
        return DataProvider.executeNonQuery(sql, maLH, maHV);
    }

    public boolean xoaHocVienKhoiLop(String maLH, String maHV) throws SQLException {
        String sql = "DELETE FROM LopHoc_HocVien WHERE MaLH = ? AND MaHV = ?";
        return DataProvider.executeNonQuery(sql, maLH, maHV);
    }

    public boolean xoaLopHoc(String maLH) throws SQLException {
        // Xóa học viên khỏi lớp trước
        String sql1 = "DELETE FROM LopHoc_HocVien WHERE MaLH = ?";
        DataProvider.executeNonQuery(sql1, maLH);
        
        // Sau đó xóa lớp
        String sql2 = "DELETE FROM LopHoc WHERE MaLH = ?";
        return DataProvider.executeNonQuery(sql2, maLH);
    }
}