package frontend.components.librarian;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import backend.controllers.LibrarianController;
import backend.models.BorrowSlip;
import backend.utils.FetchBE;

import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViolationForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField pminp;
	private JTextField tkinp;
	private JTextField stpInp;
	private JTextField lydoInp;

	public ViolationForm(BorrowSlip br, JFrame parent) {
		initialize(br,parent);
	}

	/**
	 * Create the frame.
	 */
	private void initialize(BorrowSlip br,JFrame parent) {
		setBounds(300,100,800,500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Tạo phiếu vi phạm");
		getContentPane().setLayout(null);
		
		JLabel Title = new JLabel("TẠO PHIẾU VI PHẠM");
		Title.setForeground(Color.GRAY);
		Title.setFont(new Font("Tahoma", Font.BOLD, 25));
		Title.setBounds(84, 96, 354, 31);
		getContentPane().add(Title);
		
		
		//Quay lại trang trước
		JButton ql = new JButton("Quay lại");
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		ql.setBounds(25, 23, 89, 23);
		getContentPane().add(ql);
		
		
		//Nhập dữ liệu
		JLabel pm = new JLabel("Mã phiếu mượn");
		pm.setBounds(24, 189, 100, 14);
		getContentPane().add(pm);
		
		pminp = new JTextField(br.getMaPhieuMuon());
		pminp.setBounds(134, 186, 128, 20);
		getContentPane().add(pminp);
		pminp.setColumns(10);
		
		JLabel tk = new JLabel("Mã tài khoản");
		tk.setBounds(25, 254, 100, 14);
		getContentPane().add(tk);
		
		tkinp = new JTextField(br.getMaTaiKhoan());
		tkinp.setColumns(10);
		tkinp.setBounds(134, 251, 128, 20);
		getContentPane().add(tkinp);
		
		JLabel lydo = new JLabel("Lý do");
		lydo.setBounds(25, 334, 89, 14);
		getContentPane().add(lydo);
		
		lydoInp = new JTextField();
		lydoInp.setBounds(134, 331, 436, 20);
		getContentPane().add(lydoInp);
		lydoInp.setColumns(10);
		
		JLabel songay = new JLabel("Số ngày vi phạm");
		songay.setBounds(371, 189, 100, 14);
		getContentPane().add(songay);
		
		JSpinner songayInp = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		songayInp.setBounds(462, 186, 30, 20);
		getContentPane().add(songayInp);
		
		JLabel stp = new JLabel("Số tiền phạt");
		stp.setBounds(371, 254, 100, 14);
		getContentPane().add(stp);
		
		stpInp = new JTextField();
		stpInp.setBounds(462, 251, 86, 20);
		getContentPane().add(stpInp);
		stpInp.setColumns(10);
		
		
		//Tạo phiếu vi phạm
		JButton Submit = new JButton("Tạo");
		Submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
		            double stpValue = Double.parseDouble(stpInp.getText());
		            if (stpValue <= 0) {
		                JOptionPane.showMessageDialog(ViolationForm.this, "Số tiền phạt không hợp lệ");
		                return;
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(ViolationForm.this, "Số tiền phạt không hợp lệ");
		            return;
		        }
				if(FetchBE.findV(pminp.getText(), tkinp.getText())==false) {
					JOptionPane.showMessageDialog(ViolationForm.this,"Mã phiếu mượn hoặc mã tài khoản không tồn tại");
					return;
				}
				if(pminp.getText().equals("")||tkinp.getText().equals("")||lydoInp.getText().equals("")||stpInp.getText().equals("")) {
					JOptionPane.showMessageDialog(ViolationForm.this,"Vui lòng điền đày đủ thông tin");
					return;
				}
				if(LibrarianController.addViolation(pminp.getText(),tkinp.getText(),lydoInp.getText(),(Integer)songayInp.getValue(),stpInp.getText())) {
					JOptionPane.showMessageDialog(ViolationForm.this,"Tạo phiếu vi phạm thành công");
				}
				else JOptionPane.showMessageDialog(ViolationForm.this,"Không thể kết nối");
			}
		});
		Submit.setBounds(382, 385, 89, 23);
		getContentPane().add(Submit);
		
		//reset dữ liệu
		JButton btnHu = new JButton("Huỷ");
		btnHu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pminp.setText(br.getMaPhieuMuon());
				tkinp.setText(br.getMaTaiKhoan());
				lydoInp.setText("");
				songayInp.setValue(1);
				stpInp.setText("");
			}
		});
		btnHu.setBounds(524, 385, 89, 23);
		getContentPane().add(btnHu);
		
		
		//Chuyển hưởng đến danh sách phiếu vi phạm
		JButton list = new JButton("Danh sách phiếu vi phạm");
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViolationsList vs=new ViolationsList(ViolationForm.this);
				vs.setVisible(true);
				setVisible(false);
				dispose();
				}
			});
		list.setBounds(462, 23, 216, 23);
		getContentPane().add(list);
	}
}
