package frontend.components.user;

import backend.models.Account;
import backend.utils.ReadData;
import backend.utils.WriteData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Register {
    public Register() {
    }

    public static void showRegisterLayout(JFrame frame) {

        // Tạo một JFrame cho layout đăng ký
        final JFrame registerFrame = new JFrame("Đăng ký");
        registerFrame.setSize(400, 450);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLayout(null);

        // Tạo các thành phần UI cho layout đăng ký
        JLabel lblNewLabel = new JLabel("Đăng ký tài khoản");
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblNewLabel.setForeground(Color.gray);

        JLabel lblName = new JLabel("Tên người dùng:");
        JTextField txtName = new JTextField();

        JLabel lblAddress = new JLabel("Địa chỉ:");
        JTextField txtAddress = new JTextField();

        JLabel lblPhoneNumber = new JLabel("Số điện thoại:");
        JTextField txtPhoneNumber = new JTextField();

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        JTextField txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Mật khẩu:");
        JPasswordField txtPassword = new JPasswordField();
        JLabel lblPasswordHint = new JLabel("Mật khẩu phải có ít nhất 5 ký tự gồm 1 chữ in hoa, 1 chữ số");
        lblPasswordHint.setForeground(Color.GRAY);

        JLabel lblConfirmPassword = new JLabel("Xác nhận mật khẩu:");
        JPasswordField txtConfirmPassword = new JPasswordField();

        JLabel lblMismatchMessage = new JLabel("");
        lblMismatchMessage.setForeground(Color.RED);

        JButton btnRegister = new JButton("Đăng ký");
        JButton btnBack = new JButton("Quay lại");

        lblNewLabel.setBounds(90,10,300,30);

        lblName.setBounds(50, 60, 120, 20);
        txtName.setBounds(200, 60, 120, 20);

        lblAddress.setBounds(50, 100, 120, 20);
        txtAddress.setBounds(200, 100, 120, 20);

        lblPhoneNumber.setBounds(50, 140, 120, 20);
        txtPhoneNumber.setBounds(200, 140, 120, 20);

        lblUsername.setBounds(50, 180, 120, 20);
        txtUsername.setBounds(200, 180, 120, 20);

        lblPassword.setBounds(50, 220, 120, 20);
        txtPassword.setBounds(200, 220, 120, 20);

        lblPasswordHint.setBounds(30, 260, 350, 20);

        lblConfirmPassword.setBounds(50, 300, 120, 20);
        txtConfirmPassword.setBounds(200, 300, 120, 20);

        lblMismatchMessage.setBounds(50, 320, 300, 20);

        btnRegister.setBounds(50, 360, 100, 20);
        btnBack.setBounds(250, 360, 100, 20);

        // Thêm các thành phần UI vào JFrame
        registerFrame.add(lblNewLabel);
        registerFrame.add(lblName);
        registerFrame.add(txtName);
        registerFrame.add(lblAddress);
        registerFrame.add(txtAddress);
        registerFrame.add(lblPhoneNumber);
        registerFrame.add(txtPhoneNumber);
        registerFrame.add(lblUsername);
        registerFrame.add(txtUsername);
        registerFrame.add(lblPassword);
        registerFrame.add(lblPasswordHint);
        registerFrame.add(txtPassword);
        registerFrame.add(lblConfirmPassword);
        registerFrame.add(txtConfirmPassword);
        registerFrame.add(lblMismatchMessage);
        registerFrame.add(btnRegister);
        registerFrame.add(btnBack);

        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String password = new String(txtPassword.getPassword());
                if (!isValidPassword(password)) {
                    lblPasswordHint.setForeground(Color.RED);
                } else {
                    lblPasswordHint.setForeground(Color.GRAY);
                }
            }
        });

        // Xử lý sự kiện khi nhấn vào nút "Đăng ký"
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý logic đăng ký ở đây

                String name = txtName.getText();
                String address = txtAddress.getText();
                String phoneNumber = txtPhoneNumber.getText();
                String password = new String(txtPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());
                String userName = txtUsername.getText();

                // kiểm tra xem các trường đã nhập đúng chưa và thực hiện đăng ký
                if (userName.isEmpty() || name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
                    lblMismatchMessage.setText("Vui lòng nhập đủ thông tin!");
                } else if (!password.equals(confirmPassword)) {
                    lblMismatchMessage.setText("Mật khẩu không khớp");
                } else if (!isValidPassword(password)) {
                    lblMismatchMessage.setText("Mật khẩu không hợp lệ");
                } else {
                    int total = ReadData.readAccount("../DemoDB/user-account.txt").size();
                    lblMismatchMessage.setText("Đăng ký tài khoản thành công!");
                    Account a = new Account("DG" + (total + 1), name, address, phoneNumber, userName, password, true);
                    java.util.List<Account> accounts = ReadData.readAccount("../DemoDB/user-account.txt");
                    accounts.add(a);
                    WriteData.writeAccount(accounts, "../DemoDB/user-account.txt");
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            registerFrame.dispose();
                            frame.setVisible(true);
                        }
                    });

                    timer.setRepeats(false); // Ensure the timer only runs once
                    timer.start();
                }
            }
        });

        // Xử lý sự kiện khi nhấn vào nút "Quay lại"
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Đóng cửa sổ đăng ký
                registerFrame.dispose();
                frame.setVisible(true);
            }
        });

        // Đảm bảo JFrame hiển thị ở trung tâm màn hình
        registerFrame.setLocationRelativeTo(null);

        // Hiển thị JFrame đăng ký
        registerFrame.setVisible(true);
    }

    private static boolean isValidPassword(String password) {
        if (password.length() < 5) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }
        return hasUppercase && hasNumber;
    }

}
