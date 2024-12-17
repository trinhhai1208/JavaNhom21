package frontend.components.librarian;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

import backend.controllers.LibrarianController;
import backend.models.Librarian;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePassword extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPasswordField oldPassInp;
	private JPasswordField newPassInp;
	private JPasswordField rePassInp;



	/**
	 * Create the application.
	 */
	public ChangePassword(Librarian l,JFrame parent) {
		initialize(l,parent);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Librarian l,JFrame parent) {
		setTitle("Đổi mật khẩu");
		setBounds(300, 100, 400, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		
		//form đổi mật khẩu
		JLabel title = new JLabel("Đổi mật khẩu");
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setForeground(new Color(128, 128, 128));
		title.setBounds(136, 69, 162, 48);
		getContentPane().add(title);

		JLabel oldPass = new JLabel("Nhập mật khẩu cũ");
		oldPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		oldPass.setBounds(63, 132, 119, 14);
		getContentPane().add(oldPass);

		JLabel newPass = new JLabel("Nhập mật khẩu mới");
		newPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		newPass.setBounds(63, 229, 119, 14);
		getContentPane().add(newPass);

		JLabel rePass = new JLabel("Xác nhận mật khẩu");
		rePass.setFont(new Font("Tahoma", Font.BOLD, 11));
		rePass.setBounds(63, 323, 119, 14);
		getContentPane().add(rePass);

		oldPassInp = new JPasswordField();
		oldPassInp.setBounds(63, 177, 230, 20);
		getContentPane().add(oldPassInp);

		newPassInp = new JPasswordField();
		newPassInp.setBounds(63, 267, 230, 20);
		getContentPane().add(newPassInp);

		rePassInp = new JPasswordField();
		rePassInp.setBounds(63, 352, 230, 20);
		getContentPane().add(rePassInp);

		//Đổi mật khẩu
		JButton submit = new JButton("Đổi mật khẩu");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String op=new String(oldPassInp.getPassword());
				String np=new String(newPassInp.getPassword());
				String cnp=new String(rePassInp.getPassword());
				if(op.equals("")||np.equals("")||cnp.equals("")) {
					JOptionPane.showMessageDialog(ChangePassword.this,"Vui lòng nhập đầy đủ thông tin");
					return;
				}
				if(!op.equals(l.getPassword())) {
					JOptionPane.showMessageDialog(ChangePassword.this, "Mật khẩu không chính xác");
					return;
				}
				if(!np.equals(cnp)) {
					JOptionPane.showMessageDialog(ChangePassword.this, "Mật khẩu không trùng khớp");
					return;
				}
				if(LibrarianController.changePassword(l, cnp)) {
					JOptionPane.showMessageDialog(ChangePassword.this,"Đổi mật khẩu thành công vui lòng nhấn quay lại để tiếp tục");
				}

			}
		});
		submit.setForeground(new Color(0, 0, 0));
		submit.setBackground(new Color(128, 255, 255));
		submit.setBounds(147, 404, 113, 23);
		getContentPane().add(submit);

		//Quay lại trang trước đo
		JButton ql = new JButton("Quay lại");
		ql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		ql.setBounds(21, 27, 89, 23);
		getContentPane().add(ql);
	}
}
