package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet; // Thêm import này
import java.sql.SQLException; // Thêm import này
import dbconect.DAL_HocVien;

public class QuanLyHocVienForm extends JFrame {
    private JPanel mainPanel;
    private JTable gridview;
    private DefaultTableModel tableModel;
    
    private JTextField txtMaHV;
    private JTextField txtTenHV;
    private JTextField txtDienThoai;
    private JTextField txtDiaChi;
    private JTextField txtTenSearch;
    
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnHuy;
    private JButton btnLamMoi;
    private JButton btnThoat;
    private JButton btnTim;
    
    private DAL_HocVien dal;
    private boolean luu;

    public QuanLyHocVienForm() {
        dal = new DAL_HocVien();
        initComponents();
        loadData("");
        setControlState(true);
    }

    private void initComponents() {
        setTitle("Quản lý học viên");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout(5, 5));
        setContentPane(mainPanel);

        // Form Panel (North)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã học viên:"), gbc);
        gbc.gridx = 1;
        txtMaHV = new JTextField(20);
        formPanel.add(txtMaHV, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tên học viên:"), gbc);
        gbc.gridx = 3;
        txtTenHV = new JTextField(20);
        formPanel.add(txtTenHV, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1;
        txtDienThoai = new JTextField(20);
        formPanel.add(txtDienThoai, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 3;
        txtDiaChi = new JTextField(20);
        formPanel.add(txtDiaChi, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnLamMoi = new JButton("Làm mới");
        btnThoat = new JButton("Thoát");
        
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
        searchPanel.add(new JLabel("Tên học viên:"));
        searchPanel.add(txtTenSearch);
        searchPanel.add(btnTim);

        // Table
        String[] columnNames = {"Mã học viên", "Tên học viên", "Điện thoại", "Địa chỉ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gridview = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gridview);

        // Add all panels to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.PAGE_END);

        // Add events
        addEvents();
    }

    private void addEvents() {
        btnThem.addActionListener(e -> btnThem_Click());
        btnSua.addActionListener(e -> btnSua_Click());
        btnXoa.addActionListener(e -> btnXoa_Click());
        btnLuu.addActionListener(e -> btnLuu_Click());
        btnHuy.addActionListener(e -> btnHuy_Click());
        btnLamMoi.addActionListener(e -> btnLamMoi_Click());
        btnThoat.addActionListener(e -> dispose());
        btnTim.addActionListener(e -> loadData(txtTenSearch.getText()));

        gridview.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = gridview.getSelectedRow();
                if (row >= 0) {
                    txtMaHV.setText(gridview.getValueAt(row, 0).toString());
                    txtTenHV.setText(gridview.getValueAt(row, 1).toString());
                    txtDienThoai.setText(gridview.getValueAt(row, 2).toString());
                    txtDiaChi.setText(gridview.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private void loadData(String tenHV) {
        tableModel.setRowCount(0); // Xóa tất cả dữ liệu cũ
        
        try {
            ResultSet rs = dal.danhSachHocVien(tenHV);
            while (rs.next()) {
                // Thêm dòng mới vào table model
                tableModel.addRow(new Object[]{
                    rs.getString("MaHV"),
                    rs.getString("TenHV"), 
                    rs.getString("DienThoai"),
                    rs.getString("DiaChi")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi tải dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
        
        txtMaHV.setEnabled(!state);
        txtTenHV.setEnabled(!state);
        txtDienThoai.setEnabled(!state);
        txtDiaChi.setEnabled(!state);
    }

    private void clearFields() {
        txtMaHV.setText("");
        txtTenHV.setText("");
        txtDienThoai.setText("");
        txtDiaChi.setText("");
    }

    private void btnThem_Click() {
        clearFields();
        setControlState(false);
        luu = true;
    }

    private void btnSua_Click() {
        if (gridview.getRowCount() == 0) return;
        luu = false;
        setControlState(false);
        txtMaHV.setEnabled(false);
    }

    private void btnXoa_Click() {
    	 if (gridview.getSelectedRow() < 0) {
    	        JOptionPane.showMessageDialog(this, 
    	            "Vui lòng chọn học viên cần xóa",
    	            "Thông báo",
    	            JOptionPane.WARNING_MESSAGE);
    	        return;
    	    }

    	    int result = JOptionPane.showConfirmDialog(this,
    	        "Học viên này sẽ bị xóa khỏi tất cả các lớp học.\nBạn có chắc chắn muốn xóa không?",
    	        "Xác nhận xóa",
    	        JOptionPane.YES_NO_OPTION,
    	        JOptionPane.WARNING_MESSAGE);

    	    if (result == JOptionPane.YES_OPTION) {
    	        try {
    	            String maHV = gridview.getValueAt(gridview.getSelectedRow(), 0).toString();
    	            boolean success = dal.xoaHocVien(maHV);
    	            
    	            if (success) {
    	                JOptionPane.showMessageDialog(this, 
    	                    "Xóa học viên thành công",
    	                    "Thông báo",
    	                    JOptionPane.INFORMATION_MESSAGE);
    	                loadData("");
    	            } else {
    	                JOptionPane.showMessageDialog(this,
    	                    "Xóa học viên thất bại",
    	                    "Lỗi",
    	                    JOptionPane.ERROR_MESSAGE);
    	            }
    	            
    	        } catch (SQLException ex) {
    	            JOptionPane.showMessageDialog(this,
    	                "Lỗi xóa học viên: " + ex.getMessage(),
    	                "Lỗi",
    	                JOptionPane.ERROR_MESSAGE);
    	            ex.printStackTrace();
    	        }
    	    }
    }

    private void btnLuu_Click() {
        if (txtMaHV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã học viên không được trống");
            txtMaHV.requestFocus();
            return;
        }
        if (txtTenHV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên học viên không được trống");
            txtTenHV.requestFocus();
            return;
        }

        try {
            if (luu) {
                dal.themHocVien(
                    txtMaHV.getText().trim(),
                    txtTenHV.getText().trim(),
                    txtDienThoai.getText().trim(),
                    txtDiaChi.getText().trim()
                );
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                dal.suaHocVien(
                    txtMaHV.getText().trim(),
                    txtTenHV.getText().trim(),
                    txtDienThoai.getText().trim(),
                    txtDiaChi.getText().trim()
                );
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            }
            loadData("");
            setControlState(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Mã học viên đã tồn tại, vui lòng tạo mã khác",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            txtMaHV.requestFocus();
        }
    }

    private void btnHuy_Click() {
        loadData("");
        setControlState(true);
        if (gridview.getRowCount() > 0) {
            txtMaHV.setText(gridview.getValueAt(0, 0).toString());
            txtTenHV.setText(gridview.getValueAt(0, 1).toString());
            txtDienThoai.setText(gridview.getValueAt(0, 2).toString());
            txtDiaChi.setText(gridview.getValueAt(0, 3).toString());
        }
    }

    private void btnLamMoi_Click() {
        loadData("");
        setControlState(true);
        if (gridview.getRowCount() > 0) {
            txtMaHV.setText(gridview.getValueAt(0, 0).toString());
            txtTenHV.setText(gridview.getValueAt(0, 1).toString());
            txtDienThoai.setText(gridview.getValueAt(0, 2).toString());
            txtDiaChi.setText(gridview.getValueAt(0, 3).toString());
        }
    }

    
}