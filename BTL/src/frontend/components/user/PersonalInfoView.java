package frontend.components.user;

import backend.models.Account;
import backend.utils.SessionManager;

import javax.swing.*;
import java.awt.*;

public class PersonalInfoView extends JFrame {

    public PersonalInfoView(JFrame parent) {
        // Đặt tiêu đề
        setTitle("Thông tin cá nhân");

        // Get current user details
        Account currentUser = SessionManager.getCurrentUser();

        // Tạo
        JLabel headerLabel = new JLabel("Thông tin cá nhân");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.gray);

        JLabel nameLabel = new JLabel("Tên tài khoản: ");
        JLabel nameValue = new JLabel(currentUser.getTenDangNhap());
        JLabel addressLabel = new JLabel("Địa chỉ: ");
        JLabel addressValue = new JLabel(currentUser.getDiaChi());
        JLabel phoneNumberLabel = new JLabel("Số điện thoại: ");
        JLabel phoneNumberValue = new JLabel(currentUser.getSoDienThoai());
        JLabel fullNameLabel = new JLabel("Tên người dùng: ");
        JLabel fullNameValue = new JLabel(currentUser.getTenNguoiDung());

        // Thêm nút

        JButton editButton = new JButton("Chỉnh sửa thông tin cá nhân");
        JButton changePasswordButton = new JButton("Đổi mật khẩu");
        JButton backButton = new JButton("Quay lại");

        // Set button actions
        //Nút "Chỉnh sửa thông tin cá nhân"
        editButton.addActionListener(e -> {
            //Mở EditPersonalInfo window
            new EditPersonalInfo(this, currentUser);
            dispose();
        });

        // Nút "Đổi mật khẩu"
        changePasswordButton.addActionListener(e -> {
            // Mở ChangePassword window
            new ChangePassword(this, currentUser);
            dispose();
        });

        backButton.addActionListener(e -> {
            // Return to dashboard
            parent.setVisible(true);
            dispose();
        });

        // Create a top panel for the back button and header
        JPanel topPanel = new JPanel(new BorderLayout());
        // Add an empty border to the top panel to move the "Quay lại" button down slightly
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.add(backButton, BorderLayout.WEST);
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(headerLabel);
        topPanel.add(headerPanel, BorderLayout.CENTER);

        // Configure layout using GroupLayout for main content
        JPanel mainPanel = new JPanel();
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameLabel)
                                .addComponent(fullNameLabel)
                                .addComponent(addressLabel)
                                .addComponent(phoneNumberLabel))
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameValue)
                                .addComponent(fullNameValue)
                                .addComponent(addressValue)
                                .addComponent(phoneNumberValue)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameValue))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fullNameLabel)
                        .addComponent(fullNameValue))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addressLabel)
                        .addComponent(addressValue))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(phoneNumberLabel)
                        .addComponent(phoneNumberValue))
        );

        // Create a bottom panel for the buttons using BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(editButton, BorderLayout.WEST);
        bottomPanel.add(changePasswordButton, BorderLayout.EAST);

        // Add an empty border to the bottom panel to move the buttons up slightly
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Adjust the bottom padding as needed

        // Set window properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add components to the frame
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }
}
