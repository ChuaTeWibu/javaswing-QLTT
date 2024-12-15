package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import dbconect.DAL_MonHoc;

public class QuanLyMonHocForm extends JFrame {
    private JPanel mainPanel;
    private JTable gridview;
    private DefaultTableModel tableModel;
    
    private JTextField txtMaMH;
    private JTextField txtTenMH;
    private JTextField txtTenSearch;
    
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnHuy;
    private JButton btnLamMoi;
    private JButton btnThoat;
    private JButton btnTim;
    
    private DAL_MonHoc dal;
    private boolean luu;

    public QuanLyMonHocForm() {
        dal = new DAL_MonHoc();
        initComponents();
        loadData("");
        setControlState(true);
    }

    private void initComponents() {
    	setTitle("Quản lý môn học");
        setSize(800, 600); // Tăng chiều cao form
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Form Panel (North)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã môn học:"), gbc);
        gbc.gridx = 1;
        txtMaMH = new JTextField(20);
        formPanel.add(txtMaMH, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tên môn học:"), gbc);
        gbc.gridx = 3;
        txtTenMH = new JTextField(20);
        formPanel.add(txtTenMH, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Set size cho các button
        Dimension buttonSize = new Dimension(100, 30);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnLamMoi = new JButton("Làm mới");
        btnThoat = new JButton("Thoát");
        
        // Set preferred size cho các button
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
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtTenSearch = new JTextField(20);
        btnTim = new JButton("Tìm kiếm");
        btnTim.setPreferredSize(buttonSize);
        searchPanel.add(new JLabel("Tên môn học:"));
        searchPanel.add(txtTenSearch);
        searchPanel.add(btnTim);

        // Table Panel
        String[] columns = {"Mã môn học", "Tên môn học"};
        tableModel = new DefaultTableModel(columns, 0);
        gridview = new JTable(tableModel);
        gridview.setRowHeight(25);
        
        // Thiết lập độ rộng các cột
        gridview.getColumnModel().getColumn(0).setPreferredWidth(100);
        gridview.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(gridview);
        scrollPane.setPreferredSize(new Dimension(750, 300));

        // Center Panel để chứa button và search panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(searchPanel, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Add event listeners
        addEventListeners();
    }

    private void addEventListeners() {
        btnThem.addActionListener(e -> btnThem_Click());
        btnSua.addActionListener(e -> btnSua_Click());
        btnXoa.addActionListener(e -> btnXoa_Click());
        btnLuu.addActionListener(e -> btnLuu_Click());
        btnHuy.addActionListener(e -> btnHuy_Click());
        btnLamMoi.addActionListener(e -> btnLamMoi_Click());
        btnThoat.addActionListener(e -> dispose());
        btnTim.addActionListener(e -> loadData(txtTenSearch.getText()));
        
        gridview.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = gridview.getSelectedRow();
                if (row >= 0) {
                    txtMaMH.setText(gridview.getValueAt(row, 0).toString());
                    txtTenMH.setText(gridview.getValueAt(row, 1).toString());
                }
            }
        });
    }

    private void loadData(String tenMH) {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = dal.danhSachMonHoc(tenMH);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("MaMH"),
                    rs.getString("TenMH")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi tải dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
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
        txtMaMH.setEnabled(!state);
        txtTenMH.setEnabled(!state);
    }

    private void btnThem_Click() {
        txtMaMH.setText("");
        txtTenMH.setText("");
        setControlState(false);
        luu = true;
    }

    private void btnSua_Click() {
        if (gridview.getRowCount() == 0) return;
        luu = false;
        setControlState(false);
        txtMaMH.setEnabled(false);
    }

    private void btnXoa_Click() {
        if (gridview.getRowCount() == 0) return;
        
        int result = JOptionPane.showConfirmDialog(this,
            "Có chắc chắn xóa môn học này không?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            try {
                dal.xoaMonHoc(txtMaMH.getText());
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                loadData("");
                setControlState(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi xóa môn học: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnLuu_Click() {
        if (!validateInput()) return;
        
        try {
            if (luu) {
                dal.themMonHoc(txtMaMH.getText().trim(), txtTenMH.getText().trim());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                dal.suaMonHoc(txtMaMH.getText().trim(), txtTenMH.getText().trim());
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            }
            loadData("");
            setControlState(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput() {
        if (txtMaMH.getText().trim().isEmpty()) {
            showError("Mã môn học không được trống", txtMaMH);
            return false;
        }
        if (txtTenMH.getText().trim().isEmpty()) {
            showError("Tên môn học không được trống", txtTenMH);
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

    private void btnHuy_Click() {
        loadData("");
        setControlState(true);
    }

    private void btnLamMoi_Click() {
        txtTenSearch.setText("");
        loadData("");
        setControlState(true);
    }

  
}