package frontend.components.user;

import backend.controllers.UserController;
import backend.models.Account;
import backend.utils.ReadData;
import backend.utils.SessionManager;
import backend.utils.WriteData;

import javax.swing.*;
import java.awt.*;

public class EditPersonalInfo extends JFrame {

    public EditPersonalInfo(JFrame parent, Account currentUser) {
        // Set window title
        setTitle("Chỉnh sửa thông tin cá nhân");

        // Create and configure components
        JLabel headerLabel = new JLabel("Chỉnh sửa thông tin cá nhân");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.gray);

        JLabel usernameLabel = new JLabel("Tên tài khoản:");
        JLabel fullNameLabel = new JLabel("Tên đầy đủ:");
        JLabel emailLabel = new JLabel("Địa chỉ:");
        JLabel phoneNumberLabel = new JLabel("Số điện thoại:");

        JTextField usernameField = new JTextField(currentUser.getTenDangNhap(), 21);
        JTextField fullNameField = new JTextField(currentUser.getTenNguoiDung(), 21);
        JTextField dcField = new JTextField(currentUser.getDiaChi(), 21);
        JTextField phoneField = new JTextField(currentUser.getSoDienThoai(), 21);

        JButton saveButton = new JButton("Lưu");
        JButton changePasswordButton = new JButton("Đổi mật khẩu");
        JButton backButton = new JButton("Quay lại");

        // Set button actions
        saveButton.addActionListener(e -> {

            // Validate input fields
            if (usernameField.getText().isEmpty() || fullNameField.getText().isEmpty() ||
                    dcField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Save the updated information
            currentUser.setTenDangNhap(usernameField.getText());
            currentUser.setTenNguoiDung(fullNameField.getText());
            currentUser.setDiaChi(dcField.getText());
            currentUser.setSoDienThoai(phoneField.getText());
            // Update the session user details

            if(UserController.editPersonalInfo(currentUser)){
                // Hiện thông báo thành công
                JOptionPane.showMessageDialog(this, "Thông tin đã được cập nhật", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            }
            // Quay lại personal info view
            new Dashboard();
            dispose();
        });

        // Đổi mật khẩu
        changePasswordButton.addActionListener(e -> {
            // Mở ChangePassword window
            new ChangePassword(this, currentUser);
            dispose();
        });

        // Quay lại
        backButton.addActionListener(e -> {
            // quay lại personal info view
            new Dashboard();
            dispose();
        });

        // Configure layout using GroupLayout
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameLabel)
                                .addComponent(fullNameLabel)
                                .addComponent(emailLabel)
                                .addComponent(phoneNumberLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameField)
                                .addComponent(fullNameField)
                                .addComponent(dcField)
                                .addComponent(phoneField)))
                .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                        .addComponent(headerLabel))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changePasswordButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(headerLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fullNameLabel)
                        .addComponent(fullNameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emailLabel)
                        .addComponent(dcField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(phoneNumberLabel)
                        .addComponent(phoneField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton)
                        .addComponent(changePasswordButton)
                        .addComponent(backButton)));

//        // Đặt thuộc tính cửa sổ
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(panel);
        setVisible(true);
    }
}
