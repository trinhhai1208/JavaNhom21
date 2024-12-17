package frontend.components.user;

import backend.controllers.UserController;
import backend.models.Account;
import backend.utils.SessionManager;

import javax.swing.*;
import java.awt.*;

public class UserLogin extends JFrame {
    public UserLogin(JFrame parent) {

        // Đặt tiêu đề
        setTitle("Độc giả đăng nhập");
        setResizable(false);

        // Tạo và cấu hình các thành phần
        JLabel headerLabel = new JLabel("Đăng nhập");
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        headerLabel.setForeground(Color.gray);

        JLabel usernameLabel = new JLabel("Tên tài khoản:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Đăng nhập");
        JButton backButton = new JButton("Quay lại");

        // tạo bảng điều khiển nút quay lại
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);

        //
        //Tạo bảng điều khiển cho biểu mẫu đăng nhập chính
        JPanel loginPanel = new JPanel();
        GroupLayout layout = new GroupLayout(loginPanel);
        loginPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(headerLabel)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameLabel)
                                .addComponent(passwordLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameField)
                                .addComponent(passwordField)))
                .addComponent(loginButton));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(headerLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addComponent(loginButton));

        //Đặt bố cục cho ngăn nội dung chính
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(backButtonPanel, BorderLayout.NORTH);
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        //  action nút "Đăng nhập"
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            Account loggedIn = UserController.login(username, password);
            if (loggedIn != null) {
                if (loggedIn.getIsActive() == false) {
                    JOptionPane.showMessageDialog(this, "Tài khoản của bạn đã bị khoá vui lòng liên hệ thủ thư", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    SessionManager.login(loggedIn);

                    // Mở cửa sổ sang Menu User
                    Dashboard dashboard = new Dashboard();
                    dashboard.setVisible(true);

                    // Đóng cửa sổ đăng nhập
                    dispose();
                }
            } else {
                // Hiển thị lỗi
                JOptionPane.showMessageDialog(this, "Đăng nhập không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        });

        // action nút "Quay lại"
        backButton.addActionListener(e -> {
            parent.setVisible(true);
            dispose();
        });

        // Đặt kích thước
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
