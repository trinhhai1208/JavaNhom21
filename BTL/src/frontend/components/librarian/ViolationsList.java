package frontend.components.librarian;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import backend.models.Violation;
import backend.utils.FetchBE;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViolationsList extends JFrame {

	private static final long serialVersionUID = 1L;



	/**
	 * Create the frame.
	 */
	public ViolationsList(JFrame parent) {
		initialize(parent);
	}
	
	//lấy toàn bộ phiếu vi phạm
	private static void fetchViolations(DefaultTableModel m) {
		m.setRowCount(0);
		List<Violation> result=FetchBE.fetchViolation();
		for(Violation v: result) {
			 Object[] row = {v.getMaViPham(),v.getMaPhieuMuon(),v.getMaTaiKhoan(),v.getLyDo(),v.getSoNgayViPham(),v.getSoTienPhat()};
			 m.addRow(row);
		}
	}
	private void initialize(JFrame parent) {
		setTitle("Danh sách phiếu vi phạm");
		Rectangle r=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(0,0,r.width,r.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		//quay lại trang trước đo
		JButton ql = new JButton("Quay lại");
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		ql.setBounds(40, 35, 89, 23);
		getContentPane().add(ql);
		
		JLabel title = new JLabel("DANH SÁCH PHIẾU VI PHẠM");
		title.setForeground(Color.GRAY);
		title.setFont(new Font("Tahoma", Font.BOLD, 25));
		title.setBounds(551, 57, 474, 31);
		getContentPane().add(title);
		
		
		//tạo bảng
		String v[]= {"Mã vi phạm","Mã phiếu mượn","Mã tài khoản", "Lý do","Số ngày vi phạm","Số tiền phạt"};
		DefaultTableModel model=new DefaultTableModel(v,0);
		fetchViolations(model);
		JTable table=new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(39, 117, 1257, 597);
		getContentPane().add(sp);

		
	}
}
