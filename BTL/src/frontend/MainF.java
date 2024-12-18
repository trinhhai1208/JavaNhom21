package frontend;

import frontend.components.user.Register;
import frontend.components.user.UserLogin;
import frontend.components.librarian.*;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;

public class MainF {

    // giao diện chuyển hướng
    private JFrame frame;

    // Thêm getter để lấy frame chính và trở về sau khi đăng xuất
    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainF window = new MainF();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainF() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setTitle("Hệ thống quản lý sách");
        frame.setSize(560, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 560, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null); // căn ra giữa màn hình
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Hệ thống quản lý sách");
        title.setForeground(new Color(54, 54, 54));
        title.setFont(new Font("Tahoma", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(10, 20, 530, 50);
        frame.getContentPane().add(title);

        JLabel label1 = new JLabel("Đăng nhập để tiếp tục");
        label1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBounds(10, 80, 530, 30);
        frame.getContentPane().add(label1);

        JButton signup = new JButton("Đăng ký");
        signup.setFont(new Font("Tahoma", Font.PLAIN, 16));
        signup.setForeground(Color.WHITE);
        signup.setBackground(new Color(100, 149, 237));
        signup.setFocusPainted(false);
        signup.addActionListener(e -> {
            Register.showRegisterLayout(frame);
            frame.dispose();
        });
        signup.setBounds(180, 140, 200, 40);
        frame.getContentPane().add(signup);

        JButton signin = new JButton("Đăng nhập");
        signin.setFont(new Font("Tahoma", Font.PLAIN, 16));
        signin.setForeground(Color.WHITE);
        signin.setBackground(new Color(100, 149, 237));
        signin.setFocusPainted(false);
        signin.addActionListener(e -> {
            UserLogin userLogin = new UserLogin(frame);
            userLogin.setVisible(true);
            frame.setVisible(false);
        });
        signin.setBounds(180, 200, 200, 40);
        frame.getContentPane().add(signin);

        JButton signinAd = new JButton("Đăng nhập (Thủ thư)");
        signinAd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        signinAd.setForeground(Color.WHITE);
        signinAd.setBackground(new Color(100, 149, 237));
        signinAd.setFocusPainted(false);
        signinAd.addActionListener(e -> {
            AdminLogin admin = new AdminLogin(frame);
            admin.setVisible(true);
            frame.setVisible(false);
            frame.dispose();
        });
        signinAd.setBounds(180, 260, 200, 40);
        frame.getContentPane().add(signinAd);
    }
}
