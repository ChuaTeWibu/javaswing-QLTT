package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbconect.DAL_GiaoVien;
import com.toedter.calendar.JDateChooser;

public class QuanLyGiaoVienForm extends JFrame {
    private JPanel mainPanel;
    private JTable gridview;
    private DefaultTableModel tableModel;
    
    private JTextField txtMaGV;
    private JTextField txtTenGV;
    private JComboBox<String> cboGioiTinh;
    private JDateChooser dtNgaySinh;
    private JTextField txtTrinhDo;
    private JTextField txtKhoa;
    private JTextField txtGhichu;
    private JTextField txtTenGVSearch;
    
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnHuy;
    private JButton btnLamMoi;
    private JButton btnThoat;
    private JButton btnTim;
    
    private DAL_GiaoVien dal;
    private boolean luu;

    public QuanLyGiaoVienForm() {
        dal = new DAL_GiaoVien();
        initComponents();
        loadGioiTinh();
        loadData("");
        setControlState(true);
    }

    private void initComponents() {
        setTitle("Quản lý giáo viên");
        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout(5, 5));
        setContentPane(mainPanel);

        // Form Panel (North)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã giáo viên:"), gbc);
        gbc.gridx = 1;
        txtMaGV = new JTextField(15);
        formPanel.add(txtMaGV, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tên giáo viên:"), gbc);
        gbc.gridx = 3;
        txtTenGV = new JTextField(15);
        formPanel.add(txtTenGV, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        cboGioiTinh = new JComboBox<>();
        cboGioiTinh.setPreferredSize(new Dimension(150, 25));
        formPanel.add(cboGioiTinh, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 3;
        dtNgaySinh = new JDateChooser();
        dtNgaySinh.setPreferredSize(new Dimension(150, 25));
        dtNgaySinh.setDateFormatString("dd/MM/yyyy");
        formPanel.add(dtNgaySinh, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Trình độ:"), gbc);
        gbc.gridx = 1;
        txtTrinhDo = new JTextField(15);
        formPanel.add(txtTrinhDo, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Khoa:"), gbc);
        gbc.gridx = 3;
        txtKhoa = new JTextField(15);
        formPanel.add(txtKhoa, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtGhichu = new JTextField(40);
        formPanel.add(txtGhichu, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnLamMoi = new JButton("Làm mới");
        btnThoat = new JButton("Thoát");
        
        // Set size for buttons
        Dimension buttonSize = new Dimension(100, 30);
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
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtTenGVSearch = new JTextField(20);
        btnTim = new JButton("Tìm kiếm");
        btnTim.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(new JLabel("Tên giáo viên:"));
        searchPanel.add(txtTenGVSearch);
        searchPanel.add(btnTim);

        // Table
        String[] columnNames = {"Mã GV", "Tên GV", "Giới tính", "Ngày sinh", "Trình độ", "Khoa", "Ghi chú"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gridview = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp trên bảng
            }
        };
        
        // Thiết lập thuộc tính cho bảng
        gridview.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        gridview.getTableHeader().setReorderingAllowed(false);
        gridview.setRowHeight(25);
        
        // Thiết lập độ rộng các cột
        gridview.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã GV
        gridview.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên GV
        gridview.getColumnModel().getColumn(2).setPreferredWidth(80);  // Giới tính
        gridview.getColumnModel().getColumn(3).setPreferredWidth(100); // Ngày sinh
        gridview.getColumnModel().getColumn(4).setPreferredWidth(100); // Trình độ
        gridview.getColumnModel().getColumn(5).setPreferredWidth(100); // Khoa
        gridview.getColumnModel().getColumn(6).setPreferredWidth(150); // Ghi chú

        JScrollPane scrollPane = new JScrollPane(gridview);
        scrollPane.setPreferredSize(new Dimension(850, 300));

        // Center Panel để chứa button và search panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(searchPanel, BorderLayout.CENTER);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add all panels to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        // Add events
        addEvents();
    }

    private void loadGioiTinh() {
        cboGioiTinh.removeAllItems();
        cboGioiTinh.addItem("Nam");
        cboGioiTinh.addItem("Nữ");
    }

    private void loadData(String tenGV) {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = dal.danhSachGiaoVien(tenGV);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("MaGV"),
                    rs.getString("TenGV"),
                    rs.getString("GioiTinh"),
                    rs.getString("NgaySinh"),
                    rs.getString("TrinhDo"),
                    rs.getString("Khoa"),
                    rs.getString("GhiChu")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi tải dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Các phương thức khác giữ nguyên như cũ
    private void setControlState(boolean state) {
        btnThem.setEnabled(state);
        btnSua.setEnabled(state);
        btnXoa.setEnabled(state);
        btnLuu.setEnabled(!state);
        btnHuy.setEnabled(!state);
        btnLamMoi.setEnabled(state);
        btnThoat.setEnabled(state);
        btnTim.setEnabled(state);
        
        txtMaGV.setEnabled(!state);
        txtTenGV.setEnabled(!state);
        cboGioiTinh.setEnabled(!state);
        dtNgaySinh.setEnabled(!state);
        txtTrinhDo.setEnabled(!state);
        txtKhoa.setEnabled(!state);
        txtGhichu.setEnabled(!state);
    }

    private void clearFields() {
        txtMaGV.setText("");
        txtTenGV.setText("");
        cboGioiTinh.setSelectedIndex(0);
        dtNgaySinh.setDate(null);
        txtTrinhDo.setText("");
        txtKhoa.setText("");
        txtGhichu.setText("");
    }

    private void addEvents() {
        btnThem.addActionListener(e -> btnThem_Click());
        btnSua.addActionListener(e -> btnSua_Click());
        btnXoa.addActionListener(e -> btnXoa_Click());
        btnLuu.addActionListener(e -> btnLuu_Click());
        btnHuy.addActionListener(e -> btnHuy_Click());
        btnLamMoi.addActionListener(e -> btnLamMoi_Click());
        btnThoat.addActionListener(e -> dispose());
        btnTim.addActionListener(e -> loadData(txtTenGVSearch.getText()));

        gridview.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = gridview.getSelectedRow();
                if (row >= 0) {
                    txtMaGV.setText(gridview.getValueAt(row, 0).toString());
                    txtTenGV.setText(gridview.getValueAt(row, 1).toString());
                    cboGioiTinh.setSelectedItem(gridview.getValueAt(row, 2).toString());
                    // Xử lý ngày sinh
                    try {
                        java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd")
                            .parse(gridview.getValueAt(row, 3).toString());
                        dtNgaySinh.setDate(date);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    txtTrinhDo.setText(gridview.getValueAt(row, 4).toString());
                    txtKhoa.setText(gridview.getValueAt(row, 5).toString());
                    txtGhichu.setText(gridview.getValueAt(row, 6).toString());
                }
            }
        });
    }

    // Các phương thức xử lý sự kiện button
    private void btnThem_Click() {
        clearFields();
        setControlState(false);
        luu = true;
    }

    private void btnSua_Click() {
        if (gridview.getRowCount() == 0) return;
        luu = false;
        setControlState(false);
        txtMaGV.setEnabled(false);
    }

    private void btnXoa_Click() {
        if (gridview.getRowCount() == 0) return;
        
        int result = JOptionPane.showConfirmDialog(this,
            "Có chắc chắn xóa giáo viên này không?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (result == JOptionPane.YES_OPTION) {
            String maGV = gridview.getValueAt(gridview.getSelectedRow(), 0).toString();
            try {
                dal.xoaGiaoVien(maGV);
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                loadData("");
                setControlState(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi xóa giáo viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnLuu_Click() {
        if (txtMaGV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã giáo viên không được trống");
            txtMaGV.requestFocus();
            return;
        }
        if (txtTenGV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên giáo viên không được trống");
            txtTenGV.requestFocus();
            return;
        }
        if (dtNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được trống");
            dtNgaySinh.requestFocus();
            return;
        }

        try {
            String ngaySinh = new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(dtNgaySinh.getDate());
                
            if (luu) {
                dal.themGiaoVien(
                    txtMaGV.getText().trim(),
                    txtTenGV.getText().trim(),
                    cboGioiTinh.getSelectedItem().toString(),
                    ngaySinh,
                    txtTrinhDo.getText().trim(),
                    txtKhoa.getText().trim(),
                    txtGhichu.getText().trim()
                );
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                dal.suaGiaoVien(
                    txtMaGV.getText().trim(),
                    txtTenGV.getText().trim(),
                    cboGioiTinh.getSelectedItem().toString(),
                    ngaySinh,
                    txtTrinhDo.getText().trim(),
                    txtKhoa.getText().trim(),
                    txtGhichu.getText().trim()
                );
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            }
            loadData("");
            setControlState(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnHuy_Click() {
        loadData("");
        setControlState(true);
        if (gridview.getRowCount() > 0) {
            gridview.setRowSelectionInterval(0, 0);
            // Trigger cell click event
            gridview.getSelectionModel().setSelectionInterval(0, 0);
        }
    }

    private void btnLamMoi_Click() {
        txtTenGVSearch.setText("");
        loadData("");
        setControlState(true);
        if (gridview.getRowCount() > 0) {
            gridview.setRowSelectionInterval(0, 0);
            // Trigger cell click event
            gridview.getSelectionModel().setSelectionInterval(0, 0);
        }
    }
}