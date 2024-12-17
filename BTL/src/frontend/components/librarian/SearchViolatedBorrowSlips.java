package frontend.components.librarian;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import backend.models.BorrowSlip;
import backend.utils.FetchBE;
import frontend.utils.ImageProcess;

import java.awt.Font;

public class SearchViolatedBorrowSlips extends JFrame{
	private JTextField searchBar;
	private JTable table;
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the application.
	 */
	public SearchViolatedBorrowSlips(JFrame parent) {
		initialize(parent);
	}


	//lấy danh sách phiếu mượn vi phạm
	private static void fetchViolatedBorrowSlip(DefaultTableModel m) {
		m.setRowCount(0);
		List<BorrowSlip> result=FetchBE.findViolatedBS();
		for(BorrowSlip bs : result) {
			Object[] row= {bs.getMaPhieuMuon(),bs.getNgayMuon(),bs.getNgayTra(),bs.getMaTaiKhoan(),bs.getMaSach(),bs.getTrangThai()};
			m.addRow(row);
		}
	}
	
	//lấy danh sách phiếu mượn vi phạm theo dữ liệu có trước
	private static void fetchViolatedBorrowSlip(DefaultTableModel m,List<BorrowSlip> bs) {
		m.setRowCount(0);
		for(BorrowSlip s : bs) {
			Object[] row= {s.getMaPhieuMuon(),s.getNgayMuon(),s.getNgayTra(),s.getMaTaiKhoan(),s.getMaSach(),s.getTrangThai()};
			m.addRow(row);
		}
	}
	
	private void initialize(JFrame parent) {
		setTitle("Danh sách phiếu mượn vi phạm");
		Rectangle r=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(0,0,r.width,r.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		
		//thanh tìm kiếm phiếu mượn
		searchBar=new JTextField();
		searchBar.setBounds(951, 21, 276, 35);
		searchBar.setColumns(10);
		getContentPane().add(searchBar);
		
		//Tìm kiếm
		JButton search = new JButton("Tìm kiếm");
		search.setBackground(new Color(0, 128, 255));
		search.setBounds(1249, 20, 89, 30);
		getContentPane().add(search);
		
		
		
		//Tạo bảng
		String[] bs= {"Mã phiếu mượn","Ngày mượn","Ngày trả","Mã tài khoản","Mã sách","Trạng thái"};
		DefaultTableModel model=new DefaultTableModel(bs,0);
		fetchViolatedBorrowSlip(model);
		
		table = new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(39, 117, 1257, 597);
		getContentPane().add(sp);
		
		JButton ql = new JButton("Quay lại");
		ql.setBounds(63, 27, 89, 23);
		getContentPane().add(ql);
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		JLabel title = new JLabel("DANH SÁCH PHIẾU MƯỢN VI PHẠM");
		title.setForeground(Color.GRAY);
		title.setFont(new Font("Tahoma", Font.BOLD, 25));
		title.setBounds(289, 59, 474, 31);
		getContentPane().add(title);
		
		//danh sách phiếu vi phạm
		JButton list = new JButton("Danh sách phiếu vi phạm");
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViolationsList vl=new ViolationsList(SearchViolatedBorrowSlips.this);
				vl.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		list.setBounds(177, 27, 216, 23);
		getContentPane().add(list);
		
		//tải lại trang
		JButton ref = new JButton("");
		ImageIcon reload=ImageProcess.scaled("Assets/reload.png",23,23);
		ref.setIcon(reload);
		ref.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fetchViolatedBorrowSlip(model);
			}
		});
		ref.setBounds(893, 27, 23, 23);
		getContentPane().add(ref);
		
		
		//Nhấn vào hàng
		table.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) {
				int selected=table.getSelectedRow();
				String maPhieuMuon=table.getValueAt(selected, 0).toString();
				LocalDate ngayMuon=LocalDate.parse(table.getValueAt(selected, 1).toString());
				String maTaiKhoan=table.getValueAt(selected, 3).toString();
				String maSach=table.getValueAt(selected, 4).toString();
				String trangThai=table.getValueAt(selected, 5).toString();
				BorrowSlip bs=new BorrowSlip(maPhieuMuon,ngayMuon,maTaiKhoan,maSach,trangThai);
				ViolationForm vf=new ViolationForm(bs,SearchViolatedBorrowSlips.this);
				vf.setVisible(true);
			}
		});
		
		//Tìm kiếm phiếu mượn
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<BorrowSlip> result=FetchBE.findBS(searchBar.getText());
				fetchViolatedBorrowSlip(model,result);
				table.setModel(model);
			}
		});
	}
}
