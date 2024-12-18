package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import dbconect.DAL_LopHoc;

public class QuanLyLopHocForm extends JFrame {
    private JPanel mainPanel;
    private JTable gridview;
    private JTable gridviewDetail;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelDetail;
    
    private JTextField txtMaLH;
    private JComboBox<String> cboMaMH;
    private JComboBox<String> cboMaGV;
    private JDateChooser dtNgayBatDau;
    private JDateChooser dtNgayKetThuc;
    private JSpinner spnSoBuoi;
    private JTextField txtTenSearch;
    private JComboBox<String> cboMaHV;
    
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnHuy;
    private JButton btnLamMoi;
    private JButton btnThoat;
    private JButton btnTim;
    private JButton btnThemHV;
    private JButton btnXoaHV;
    
    private DAL_LopHoc dal;
    private boolean luu;

    public QuanLyLopHocForm() {
        dal = new DAL_LopHoc();
        initComponents();
        loadComboBoxes();
        loadData("");
        setControlState(true);
    }
    private void initComponents() {
        setTitle("Quản lý lớp học");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã lớp học:"), gbc);
        gbc.gridx = 1;
        txtMaLH = new JTextField(20);
        formPanel.add(txtMaLH, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Môn học:"), gbc);
        gbc.gridx = 3;
        cboMaMH = new JComboBox<>();
        formPanel.add(cboMaMH, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Giáo viên:"), gbc);
        gbc.gridx = 1;
        cboMaGV = new JComboBox<>();
        formPanel.add(cboMaGV, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
        gbc.gridx = 3;
        dtNgayBatDau = new JDateChooser();
        dtNgayBatDau.setDateFormatString("dd/MM/yyyy");
        formPanel.add(dtNgayBatDau, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ngày kết thúc:"), gbc);
        gbc.gridx = 1;
        dtNgayKetThuc = new JDateChooser();
        dtNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        formPanel.add(dtNgayKetThuc, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Số buổi:"), gbc);
        gbc.gridx = 3;
        spnSoBuoi = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        formPanel.add(spnSoBuoi, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        Dimension buttonSize = new Dimension(100, 30);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnLamMoi = new JButton("Làm mới");
        btnThoat = new JButton("Thoát");
        
        // Set size cho các button
        btnThem.setPreferredSize(buttonSize);
        btnSua.setPreferredSize(buttonSize);
        btnXoa.setPreferredSize(buttonSize);
        btnLuu.setPreferredSize(buttonSize);
        btnHuy.setPreferredSize(buttonSize);
        btnLamMoi.setPreferredSize(buttonSize);
        btnThoat.setPreferredSize(buttonSize);
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnLamMoi);
        buttonPanel.add(btnThoat);	
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTenSearch = new JTextField(20);
        btnTim = new JButton("Tìm kiếm");
        btnTim.setPreferredSize(buttonSize);
        searchPanel.add(new JLabel("Tên lớp:"));
        searchPanel.add(txtTenSearch);
        searchPanel.add(btnTim);

        // Main Table
        String[] columns = {"Mã lớp", "Môn học", "Giáo viên", "Ngày bắt đầu", "Ngày kết thúc", "Số buổi"};
        tableModel = new DefaultTableModel(columns, 0);
        gridview = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gridview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gridview.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(gridview);
        scrollPane.setPreferredSize(new Dimension(1150, 200));

        // Detail Panel (Học viên trong lớp)
        JPanel detailPanel = new JPanel(new BorderLayout(5, 5));
        detailPanel.setBorder(BorderFactory.createTitledBorder("Danh sách học viên trong lớp"));

        // Panel thêm học viên
        JPanel addStudentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboMaHV = new JComboBox<>();
        btnThemHV = new JButton("Thêm học viên");
        btnXoaHV = new JButton("Xóa học viên");
        btnThemHV.setPreferredSize(buttonSize);
        btnXoaHV.setPreferredSize(buttonSize);
        
        addStudentPanel.add(new JLabel("Học viên:"));
        addStudentPanel.add(cboMaHV);
        addStudentPanel.add(btnThemHV);
        addStudentPanel.add(btnXoaHV);

        // Detail Table
        String[] detailColumns = {"Mã HV", "Tên học viên", "Điện thoại", "Địa chỉ"};
        tableModelDetail = new DefaultTableModel(detailColumns, 0);
        gridviewDetail = new JTable(tableModelDetail) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gridviewDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gridviewDetail.setRowHeight(25);
        JScrollPane detailScrollPane = new JScrollPane(gridviewDetail);
        detailScrollPane.setPreferredSize(new Dimension(1150, 200));

        detailPanel.add(addStudentPanel, BorderLayout.NORTH);
        detailPanel.add(detailScrollPane, BorderLayout.CENTER);

        // Add all panels to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(detailPanel, BorderLayout.SOUTH);

        // Add event listeners
        addEventListeners();
    }

    private void loadComboBoxes() {
        try {
            // Load môn học
            ResultSet rsMH = dal.getDanhSachMonHoc();
            cboMaMH.removeAllItems();
            while (rsMH.next()) {
                cboMaMH.addItem(rsMH.getString("MaMH") + " - " + rsMH.getString("TenMH"));
            }

            // Load giáo viên
            ResultSet rsGV = dal.getDanhSachGiaoVien();
            cboMaGV.removeAllItems();
            while (rsGV.next()) {
                cboMaGV.addItem(rsGV.getString("MaGV") + " - " + rsGV.getString("TenGV"));
            }

            // Load học viên
            ResultSet rsHV = dal.getDanhSachHocVien();
            cboMaHV.removeAllItems();
            while (rsHV.next()) {
                cboMaHV.addItem(rsHV.getString("MaHV") + " - " + rsHV.getString("TenHV"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi load dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData(String tenLop) {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = dal.getDanhSachLopHoc(tenLop);
            while (rs != null && rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("MaLH"),  // Sửa từ "MaLop" thành "MaLH"
                    rs.getString("TenMH"),
                    rs.getString("TenGV"), 
                    rs.getTimestamp("NgayBatDau"),  // Sử dụng getTimestamp vì kiểu dữ liệu là DATETIME
                    rs.getTimestamp("NgayKetThuc"), 
                    rs.getInt("SoBuoi")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi load dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void loadDataChiTietLop(String maLop) {
        tableModelDetail.setRowCount(0);
        try {
            ResultSet rs = dal.getDanhSachHocVienTrongLop(maLop);
            while (rs.next()) {
                tableModelDetail.addRow(new Object[]{
                    rs.getString("MaHV"),
                    rs.getString("TenHV"),
                    rs.getString("DienThoai"),
                    rs.getString("DiaChi")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi load danh sách học viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEventListeners() {
        btnThem.addActionListener(e -> btnThem_Click());
        btnSua.addActionListener(e -> btnSua_Click());
        btnXoa.addActionListener(e -> btnXoa_Click());
        btnLuu.addActionListener(e -> btnLuu_Click());
        btnHuy.addActionListener(e -> btnHuy_Click());
        btnLamMoi.addActionListener(e -> btnLamMoi_Click());
        btnThoat.addActionListener(e -> dispose());
        btnTim.addActionListener(e -> loadData(txtTenSearch.getText().trim()));
        btnThemHV.addActionListener(e -> btnThemHV_Click());
        btnXoaHV.addActionListener(e -> btnXoaHV_Click());

        gridview.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = gridview.getSelectedRow();
                if (row >= 0) {
                    String maLop = gridview.getValueAt(row, 0).toString();
                    txtMaLH.setText(maLop);
                    loadDataChiTietLop(maLop);
                    
                    // Set các giá trị khác
                    String monHoc = gridview.getValueAt(row, 1).toString();
                    String giaoVien = gridview.getValueAt(row, 2).toString();
                    for (int i = 0; i < cboMaMH.getItemCount(); i++) {
                        if (cboMaMH.getItemAt(i).contains(monHoc)) {
                            cboMaMH.setSelectedIndex(i);
                            break;
                        }
                    }
                    for (int i = 0; i < cboMaGV.getItemCount(); i++) {
                        if (cboMaGV.getItemAt(i).contains(giaoVien)) {
                            cboMaGV.setSelectedIndex(i);
                            break;
                        }
                    }
                    dtNgayBatDau.setDate((java.util.Date)gridview.getValueAt(row, 3));
                    dtNgayKetThuc.setDate((java.util.Date)gridview.getValueAt(row, 4));
                    spnSoBuoi.setValue(gridview.getValueAt(row, 5));
                }
            }
        });
    }

    private void setControlState(boolean state) {
        btnThem.setEnabled(state);
        btnSua.setEnabled(state);
        btnXoa.setEnabled(state);
        btnLuu.setEnabled(!state);
        btnHuy.setEnabled(!state);
        btnLamMoi.setEnabled(state);
        btnThoat.setEnabled(state);
        btnTim.setEnabled(state);
        
        txtMaLH.setEnabled(!state);
        cboMaMH.setEnabled(!state);
        cboMaGV.setEnabled(!state);
        dtNgayBatDau.setEnabled(!state);
        dtNgayKetThuc.setEnabled(!state);
        spnSoBuoi.setEnabled(!state);
        
        btnThemHV.setEnabled(state);
        btnXoaHV.setEnabled(state);
        cboMaHV.setEnabled(state);
    }

    private void clearFields() {
        txtMaLH.setText("");
        cboMaMH.setSelectedIndex(0);
        cboMaGV.setSelectedIndex(0);
        dtNgayBatDau.setDate(new java.util.Date());
        dtNgayKetThuc.setDate(new java.util.Date());
        spnSoBuoi.setValue(1);
        tableModelDetail.setRowCount(0);
    }
    private void btnThem_Click() {
        clearFields();
        setControlState(false);
        luu = true;
        txtMaLH.requestFocus();
    }

    private void btnSua_Click() {
        if (gridview.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học cần sửa");
            return;
        }
        setControlState(false);
        luu = false;
        txtMaLH.setEnabled(false);
    }

    private void btnXoa_Click() {
        if (gridview.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học cần xóa");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa lớp học này không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                String maLop = txtMaLH.getText().trim();
                if (dal.xoaLopHoc(maLop)) {
                    JOptionPane.showMessageDialog(this, "Xóa lớp học thành công");
                    loadData("");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa lớp học thất bại");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi xóa lớp học: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnLuu_Click() {
        if (!validateInput()) return;

        try {
            String maLH = txtMaLH.getText().trim();  // Sử dụng maLH thay vì maLop
            String maMH = cboMaMH.getSelectedItem().toString().split(" - ")[0];
            String maGV = cboMaGV.getSelectedItem().toString().split(" - ")[0];
            java.sql.Timestamp ngayBD = new java.sql.Timestamp(dtNgayBatDau.getDate().getTime());
            java.sql.Timestamp ngayKT = new java.sql.Timestamp(dtNgayKetThuc.getDate().getTime());
            int soBuoi = (Integer)spnSoBuoi.getValue();

            if (luu) {
                if (dal.themLopHoc(maLH, maMH, maGV, ngayBD, ngayKT, soBuoi)) {
                    JOptionPane.showMessageDialog(this, "Thêm lớp học thành công");
                    loadData("");
                    setControlState(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm lớp học thất bại");
                }
            } else {
                if (dal.suaLopHoc(maLH, maMH, maGV, ngayBD, ngayKT, soBuoi)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật lớp học thành công");
                    loadData("");
                    setControlState(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật lớp học thất bại");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnThemHV_Click() {
        if (txtMaLH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học");
            return;
        }

        try {
            String maLH = txtMaLH.getText().trim();  // Sử dụng maLH
            String maHV = cboMaHV.getSelectedItem().toString().split(" - ")[0];

            if (dal.themHocVienVaoLop(maLH, maHV)) {
                JOptionPane.showMessageDialog(this, "Thêm học viên vào lớp thành công");
                loadDataChiTietLop(maLH);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm học viên vào lớp thất bại");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnXoaHV_Click() {
        if (gridviewDetail.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học viên cần xóa khỏi lớp");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa học viên này khỏi lớp không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                String maLop = txtMaLH.getText().trim();
                String maHV = gridviewDetail.getValueAt(gridviewDetail.getSelectedRow(), 0).toString();

                if (dal.xoaHocVienKhoiLop(maLop, maHV)) {
                    JOptionPane.showMessageDialog(this, "Xóa học viên khỏi lớp thành công");
                    loadDataChiTietLop(maLop);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa học viên khỏi lớp thất bại");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnHuy_Click() {
        setControlState(true);
        if (gridview.getSelectedRow() >= 0) {
            gridview.getSelectionModel().setSelectionInterval(
                gridview.getSelectedRow(),
                gridview.getSelectedRow());
        } else {
            clearFields();
        }
    }

    private void btnLamMoi_Click() {
        txtTenSearch.setText("");
        loadData("");
        clearFields();
        setControlState(true);
    }

    private boolean validateInput() {
        if (txtMaLH.getText().trim().isEmpty()) {
            showError("Mã lớp học không được trống", txtMaLH);
            return false;
        }

        if (dtNgayBatDau.getDate() == null) {
            showError("Ngày bắt đầu không được trống", dtNgayBatDau);
            return false;
        }

        if (dtNgayKetThuc.getDate() == null) {
            showError("Ngày kết thúc không được trống", dtNgayKetThuc);
            return false;
        }

        if (dtNgayKetThuc.getDate().before(dtNgayBatDau.getDate())) {
            showError("Ngày kết thúc phải sau ngày bắt đầu", dtNgayKetThuc);
            return false;
        }

        return true;
    }

    private void showError(String message, Component focusComponent) {
        JOptionPane.showMessageDialog(this,
            message,
            "Lỗi",
            JOptionPane.ERROR_MESSAGE);
        focusComponent.requestFocus();
    }
}