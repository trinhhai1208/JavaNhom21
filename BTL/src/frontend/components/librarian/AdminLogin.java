package frontend.components.librarian;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import backend.controllers.LibrarianController;
import backend.models.Librarian;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;

public class AdminLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField adminName;
	private JPasswordField adminPassword;

	public AdminLogin(JFrame parent) {
		initialize(parent);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame parent) {
		setTitle("Đăng nhập (Thủ thư)");
		getContentPane().setForeground(Color.GRAY);
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 20));
		setBounds(450, 200, 381, 348);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);

		JLabel Title = new JLabel("Đăng nhập (Thủ thư)");
		Title.setForeground(Color.GRAY);
		Title.setFont(new Font("Tahoma", Font.BOLD, 20));
		Title.setBounds(61, 33, 301, 68);
		getContentPane().add(Title);
		
		//form nhâph liệu
		JLabel nameLb = new JLabel("Tài khoản");
		nameLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameLb.setBounds(48, 112, 70, 14);
		getContentPane().add(nameLb);

		JLabel passwordLb = new JLabel("Mật khẩu");
		passwordLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordLb.setBounds(48, 173, 70, 14);
		getContentPane().add(passwordLb);

		//đăng nhập
		JButton submit = new JButton("Đăng nhập");
		submit.setBackground(new Color(0, 255, 255));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password=new String(adminPassword.getPassword());
				if(adminName.getText().equals("")||password.equals("")) {
					JOptionPane.showMessageDialog(AdminLogin.this,"Vui lòng nhập đầy đủ thông tin");
					return;
				}
				Librarian l=LibrarianController.login(adminName.getText(),password);
				if(l!=null) {
					LibrarianHP hp=new LibrarianHP(AdminLogin.this,l);
					hp.setVisible(true);
					setVisible(false);
					dispose();
					adminPassword.setText("");
					adminName.setText("");
				}
				else  JOptionPane.showMessageDialog(AdminLogin.this,"Tài khoản hoặc mật khẩu không chính xác");
			}
		});
		submit.setBounds(116, 246, 115, 23);
		getContentPane().add(submit);

		//form nhập liệu
		adminName = new JTextField();
		adminName.setBounds(140, 112, 153, 20);
		getContentPane().add(adminName);
		adminName.setColumns(10);

		adminPassword = new JPasswordField();
		adminPassword.setBounds(140, 172, 153, 20);
		getContentPane().add(adminPassword);

		//Quay lại trang trước
		JButton ql = new JButton("Quay lại");
		ql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				setVisible(false);
			    dispose();

			}
		});
		ql.setBounds(10, 11, 89, 23);
		getContentPane().add(ql);

	}
}
