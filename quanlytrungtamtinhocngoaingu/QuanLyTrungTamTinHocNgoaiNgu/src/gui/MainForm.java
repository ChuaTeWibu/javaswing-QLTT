package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainForm extends JFrame {
    private String quyen;
    private JMenuBar menuBar;
    
    // Menu Quản lý
    private JMenu menuQuanLy;
    private JMenuItem menuItemHocVien;
    private JMenuItem menuItemGiaoVien;
    private JMenuItem menuItemMonHoc;
    private JMenuItem menuItemLopHoc;
    private JMenuItem menuItemLichThi;
    private JMenuItem menuItemKetQua;
    private JMenuItem menuItemDSDK;
    
    // Menu Tài khoản
    private JMenu menuTaiKhoan;
    private JMenuItem menuItemDangXuat;

    public MainForm() {
        initComponents();
        setupMenuEvents();
    }

    private void initComponents() {
        // Thiết lập form
        setTitle("Quản lý trung tâm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Tạo MenuBar
        menuBar = new JMenuBar();
        
        // Menu Quản lý
        menuQuanLy = new JMenu("Quản lý");
        menuItemHocVien = new JMenuItem("Quản lý học viên");
        menuItemGiaoVien = new JMenuItem("Quản lý giáo viên");
        menuItemMonHoc = new JMenuItem("Quản lý môn học");
        menuItemLopHoc = new JMenuItem("Quản lý lớp học");
        menuItemLichThi = new JMenuItem("Quản lý lịch thi");
        menuItemKetQua = new JMenuItem("Quản lý kết quả thi");
        menuItemDSDK = new JMenuItem("Danh sách đăng ký");

        // Thêm các MenuItem vào Menu Quản lý
        menuQuanLy.add(menuItemHocVien);
        menuQuanLy.add(menuItemGiaoVien);
        menuQuanLy.add(menuItemMonHoc);
        menuQuanLy.add(menuItemLopHoc);
        menuQuanLy.add(menuItemLichThi);
        menuQuanLy.add(menuItemKetQua);
        menuQuanLy.add(menuItemDSDK);

        // Menu Tài khoản
        menuTaiKhoan = new JMenu("Tài khoản");
        menuItemDangXuat = new JMenuItem("Đăng xuất");
        menuTaiKhoan.add(menuItemDangXuat);

        // Thêm các Menu vào MenuBar
        menuBar.add(menuQuanLy);
        menuBar.add(menuTaiKhoan);

        // Set MenuBar cho Frame
        setJMenuBar(menuBar);
    }

    private void setupMenuEvents() {
        // Sự kiện đăng xuất
        menuItemDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(MainForm.this,
                    "Bạn có muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginForm().setVisible(true);
                }
            }
        });

        // Sự kiện quản lý học viên
        menuItemHocVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new QuanLyHocVienForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });

        // Sự kiện quản lý giáo viên
        menuItemGiaoVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new QuanLyGiaoVienForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });

        // Sự kiện quản lý môn học
        menuItemMonHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new QuanLyMonHocForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });

        // Sự kiện quản lý lớp học
        menuItemLopHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new QuanLyLopHocForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });

        // Sự kiện quản lý lịch thi
        menuItemLichThi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new QuanLyLichThiForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });

        // Sự kiện quản lý kết quả thi
        menuItemKetQua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new QuanLyKetQuaForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });

        // Sự kiện danh sách đăng ký
        menuItemDSDK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("ADMIN".equalsIgnoreCase(quyen)) {
                    new DanhSachDangKyForm().setVisible(true);
                } else {
                    showAccessDenied();
                }
            }
        });
    }

    private void showAccessDenied() {
        JOptionPane.showMessageDialog(this,
            "Bạn không có quyền truy cập chức năng này.",
            "Thông báo",
            JOptionPane.WARNING_MESSAGE);
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
        System.out.println("Setting role: " + quyen); // Debug
        updateMenuPermissions();
    }

    public String getQuyen() {
        return quyen;
    }

    private void updateMenuPermissions() {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(quyen);
        
        // Debug
        System.out.println("Current role: " + quyen);
        System.out.println("Is Admin: " + isAdmin);
        
        // Enable/disable menu items based on permissions
        menuItemHocVien.setEnabled(isAdmin);
        menuItemGiaoVien.setEnabled(isAdmin);
        menuItemMonHoc.setEnabled(isAdmin);
        menuItemLopHoc.setEnabled(isAdmin);
        menuItemLichThi.setEnabled(isAdmin);
        menuItemKetQua.setEnabled(isAdmin);
        menuItemDSDK.setEnabled(isAdmin);
        
        // Debug menu states
        System.out.println("Menu states:");
        System.out.println("HocVien: " + menuItemHocVien.isEnabled());
        System.out.println("GiaoVien: " + menuItemGiaoVien.isEnabled());
        System.out.println("MonHoc: " + menuItemMonHoc.isEnabled());
        System.out.println("LopHoc: " + menuItemLopHoc.isEnabled());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm mainForm = new MainForm();
                mainForm.setQuyen("ADMIN"); // Test với quyền ADMIN
                mainForm.setVisible(true);
            }
        });
    }
}