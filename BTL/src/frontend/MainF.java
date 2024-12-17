package frontend;

import frontend.components.user.Register;
import frontend.components.user.UserLogin;

import frontend.components.librarian.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainF {

	//giao diện chuyển hướng
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
		frame.setSize( 560, 360);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 400, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // căn ra giữa màn hình
		
		JLabel title = new JLabel("Hệ thống quản lý sách ");
		title.setForeground(Color.gray);
		title.setForeground(new Color(128, 128, 128));
		title.setFont(new Font("Tahoma", Font.BOLD, 25));
		title.setBounds(43, 11, 325, 97);
		frame.getContentPane().add(title);
		
		JLabel label1 = new JLabel("Đăng nhập để tiếp tục");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label1.setBounds(115, 94, 176, 14);
		frame.getContentPane().add(label1);
		
		JButton signup = new JButton("Đăng ký");
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register.showRegisterLayout(frame);
				frame.dispose();
			}
		});
		signup.setBounds(113, 146, 166, 23);
		frame.getContentPane().add(signup);

		JButton signin = new JButton("Đăng nhập");
		signin.addActionListener((e) -> {
			// Create the second window (UserLogin)
			UserLogin userLogin = new UserLogin(frame);
			// Set the second window visible
			userLogin.setVisible(true);
			// Hide the main window
			frame.setVisible(false);
		});
		signin.setBounds(113, 198, 166, 23);
		frame.getContentPane().add(signin);
		
		JButton signinAd = new JButton("Đăng nhập (Thủ thư)");
		signinAd.setBounds(113, 242, 163, 23);
		signinAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminLogin admin=new AdminLogin(frame);
				admin.setVisible(true);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		frame.getContentPane().add(signinAd);
	}
}

