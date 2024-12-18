package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dbconect.DAL_TaiKhoan;
import java.sql.ResultSet;

public class LoginForm extends JFrame {
    
    // Khai báo các components
    private JPanel mainPanel;
    private JLabel lblMaTK;
    private JLabel lblMatKhau;
    private JTextField txtMaTK;
    private JPasswordField txtMatKhau;
    private JButton btnConnect;
    private JButton btnExit;
    
    // Biến static để lưu mã tài khoản
    public static String MaTK;

    public LoginForm() {
        // Thiết lập cơ bản cho form
        setTitle("Đăng nhập hệ thống");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setResizable(false); // Không cho phép thay đổi kích thước

        // Khởi tạo components
        initComponents();
        // Thêm các sự kiện
        addEvents();
    }

    private void initComponents() {
        // Tạo panel chính với GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các components

        // Label và TextField cho Mã TK
        lblMaTK = new JLabel("Mã tài khoản:");
        txtMaTK = new JTextField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(lblMaTK, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtMaTK, gbc);

        // Label và PasswordField cho Mật khẩu
        lblMatKhau = new JLabel("Mật khẩu:");
        txtMatKhau = new JPasswordField(20);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(lblMatKhau, gbc);
        
        gbc.gridx = 1;
        mainPanel.add(txtMatKhau, gbc);

        // Panel cho các buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnConnect = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");
        
        buttonPanel.add(btnConnect);
        buttonPanel.add(btnExit);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Thêm panel chính vào frame
        add(mainPanel);
    }

    private void addEvents() {
        // Sự kiện nút Đăng nhập
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConnect_Click();
            }
        });

        // Sự kiện nút Thoát
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Thêm sự kiện Enter cho txtMatKhau
        txtMatKhau.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnConnect_Click();
                }
            }
        });
    }

    private void btnConnect_Click() {
        try {
            DAL_TaiKhoan dal = new DAL_TaiKhoan();
            String maTK = txtMaTK.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword()).trim();
            StringBuilder quyen = new StringBuilder(); // Thêm biến để nhận quyền
            
            ResultSet rs = dal.kiemTraDangNhap(maTK, matKhau, quyen); // Truyền biến quyen
            
            if (rs != null && rs.next()) {
                MaTK = maTK;
                // Sử dụng giá trị quyền từ StringBuilder
                String quyenValue = quyen.toString();
                
                // Mở form chính
                MainForm mainForm = new MainForm();
                mainForm.setQuyen(quyenValue);
                mainForm.setVisible(true);
                
                // Đóng form đăng nhập
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Sai tên đăng nhập hoặc mật khẩu",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE);
                txtMatKhau.setText("");
                txtMatKhau.requestFocus();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hiển thị form
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}











