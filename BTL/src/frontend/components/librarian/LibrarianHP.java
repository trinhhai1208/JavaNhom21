package frontend.components.librarian;


import javax.swing.JFrame;
import javax.swing.JPanel;

import backend.controllers.Statics;
import backend.models.Librarian;
import frontend.utils.Animation;
import frontend.utils.FetchData;
import frontend.utils.ImageProcess;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;


public class LibrarianHP extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable table_1;
	private JTextField searchBar;

	
	public LibrarianHP(JFrame parent,Librarian l) {
		initialize(parent,l);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	
	private void initialize(JFrame parent,Librarian l) {
		setTitle("Phần mềm quản lý thư viện");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 20));
		Rectangle r=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(0,0,r.width,r.height);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel header = new JPanel();
		header.setBackground(new Color(128, 255, 255));
		header.setBounds(0, 0, 1467, 130);
		getContentPane().add(header);
		header.setLayout(null);
		
		JLabel title = new JLabel(" THƯ VIỆN ");
		title.setForeground(new Color(128, 128, 128));
		title.setFont(new Font("Tahoma", Font.BOLD, 26));
		title.setBounds(418, 35, 269, 35);
		ImageIcon library=ImageProcess.scaled("../Assets/33874-200.png",75,75);
		title.setIcon(library);
		
		
		//phần header
		header.add(title);
		JLabel adminLogo = new JLabel();
		ImageIcon admin=ImageProcess.scaled("../Assets/1144760.png", 61, 61);
		adminLogo.setBounds(37, 28, 61, 61);
		adminLogo.setIcon(admin);
		header.add(adminLogo);
		
		JButton logout = new JButton("Đăng xuất");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				parent.setVisible(true);
			}
		});
		logout.setBounds(1176, 47, 105, 23);
		header.add(logout);
		
		//tìm kiếm và chọn loại tìm kiếm
		searchBar = new JTextField();
		
		searchBar.setBounds(841, 48, 193, 20);
		
		header.add(searchBar);
		searchBar.setColumns(10);
		
		JComboBox<String> typeS = new JComboBox<String>();
		typeS.setBounds(726, 47, 105, 22);
		typeS.addItem("Sách");
		typeS.addItem("Người dùng");
		//Animation.f_placeHolder(textField, comboBox);
		header.add(typeS);
		
		JButton search = new JButton("Tìm kiếm");
		search.setBackground(new Color(255, 255, 255));
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type=(String)typeS.getSelectedItem();
				if(type.equals("Sách")) {
					SearchBook sb=new SearchBook(LibrarianHP.this,searchBar.getText());
					sb.setVisible(true);
					setVisible(false);
					dispose();
				}
				else {
					SearchUser su=new SearchUser(LibrarianHP.this,searchBar.getText());
					su.setVisible(true);
					setVisible(false);
					dispose();
				}
				
			}
		});
		search.setBounds(1044, 47, 89, 23);
		header.add(search);
		
		//tải lại trang
		ImageIcon reload=ImageProcess.scaled("../Assets/reload.png",23,23);
		JButton ref = new JButton("");
		
		ref.setIcon(reload);
		ref.setBounds(1143, 47, 23, 23);
		header.add(ref);
		
		//logo admin
		JLabel Admin = new JLabel("Xin chào, "+l.getAccountName());
		Admin.setBounds(10, 100, 129, 19);
		header.add(Admin);
		
		//thanh điều hướng
		JPanel nav = new JPanel();
		nav.setBackground(new Color(0, 128, 255));
		nav.setBounds(0, 128, 163, 621);
		getContentPane().add(nav);
		nav.setLayout(null);
		
		//mục sách
		JLabel sach = new JLabel("    Sách");
		sach.setForeground(new Color(255, 255, 255));
		sach.setBackground(new Color(192, 192, 192));
		sach.setFont(new Font("Tahoma", Font.BOLD, 14));
		sach.setBounds(10, 37, 126, 35);
		nav.add(sach);
		ImageIcon book=ImageProcess.scaled("../Assets/151594.png", 27, 27);
		sach.setIcon(book);
		ImageIcon add=ImageProcess.scaled("../Assets/262038.png",20,20);
		ImageIcon delete=ImageProcess.scaled("../Assets/delete-icon-1864x2048-bp2i0gor.png", 20, 20);
		ImageIcon modify=ImageProcess.scaled("../Assets/modify-icon-512x512-clqmeplb.png", 20, 20);
		
		//thêm sách
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddBook a=new AddBook(LibrarianHP.this);
				a.setVisible(true);
			}
		});
		Animation.onHoover(panel_2, new Color(0,128,255), new Color(0,128,192));
		panel_2.setBackground(new Color(0, 128, 255));
		panel_2.setBounds(0, 83, 163, 40);
		nav.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel addb = new JLabel("  Thêm sách");
		addb.setBounds(0, 0, 163, 40);
		panel_2.add(addb);
		addb.setForeground(new Color(255, 255, 255));
		addb.setIcon(add);
		
		//xoá sách
		JPanel panel_2_1 = new JPanel();
		panel_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SearchBook s=new SearchBook(LibrarianHP.this,3);
				s.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		Animation.onHoover(panel_2_1, new Color(0,128,255), new Color(0,128,192));
		panel_2_1.setBackground(new Color(0, 128, 255));
		panel_2_1.setBounds(0, 123, 163, 40);
		nav.add(panel_2_1);
		panel_2_1.setLayout(null);
		
		JLabel delb = new JLabel("  Xoá sách");
		delb.setBounds(0, 0, 163, 40);
		panel_2_1.add(delb);
		delb.setForeground(new Color(255, 255, 255));
		delb.setBackground(new Color(0, 128, 255));
		delb.setIcon(delete);
		
		//sửa thông tin sách
		JPanel panel_2_2 = new JPanel();
		Animation.onHoover(panel_2_2, new Color(0,128,255), new Color(0,128,192));
		panel_2_2.setBackground(new Color(0, 128, 255));
		panel_2_2.setBounds(0, 163, 163, 40);
		nav.add(panel_2_2);
		panel_2_2.setLayout(null);
	
		JLabel modifyb = new JLabel("  Sửa thông tin sách");
		panel_2_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SearchBook s=new SearchBook(LibrarianHP.this,2);
				s.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		modifyb.setBounds(0, 0, 153, 40);
		panel_2_2.add(modifyb);
		modifyb.setForeground(new Color(255, 255, 255));
		modifyb.setIcon(modify);
		
		//mục người dùng
		JLabel useropt = new JLabel("    Người dùng");
		useropt.setForeground(Color.WHITE);
		useropt.setFont(new Font("Tahoma", Font.BOLD, 14));
		useropt.setBackground(Color.LIGHT_GRAY);
		useropt.setBounds(0, 241, 163, 27);
		ImageIcon user=ImageProcess.scaled("../Assets/1077063.png", 27, 27);
		useropt.setIcon(user);
		nav.add(useropt);
		
		// mượn/trả sách
		JPanel panel_2_3 = new JPanel();
		panel_2_3.setLayout(null);
		panel_2_3.setBackground(new Color(0, 128, 255));
		panel_2_3.setBounds(0, 279, 163, 40);
		nav.add(panel_2_3);
		Animation.onHoover(panel_2_3,new Color(0,128,255) , new Color(0,128,192));
		
		JLabel borrowApp = new JLabel("Yêu cầu mượn/trả sách");
		panel_2_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BorrowApprove ba=new BorrowApprove(LibrarianHP.this);
				ba.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		borrowApp.setForeground(Color.WHITE);
		borrowApp.setBounds(0, 0, 163, 40);
		ImageIcon borrow=ImageProcess.scaled("../Assets/borrow.png", 20, 20);
		borrowApp.setIcon(borrow);
		panel_2_3.add(borrowApp);
		
		//phiếu vi phạm
		JPanel panel_2_4 = new JPanel();
		panel_2_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SearchViolatedBorrowSlips sbs=new SearchViolatedBorrowSlips(LibrarianHP.this);
				sbs.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		panel_2_4.setLayout(null);
		panel_2_4.setBackground(new Color(0, 128, 255));
		panel_2_4.setBounds(0, 320, 163, 40);
		nav.add(panel_2_4);
		Animation.onHoover(panel_2_4, new Color(0,128,255) , new Color(0,128,192));
		
		JLabel violations = new JLabel("Phiếu vi phạm");
		violations.setForeground(Color.WHITE);
		violations.setBounds(0, 0, 163, 40);
		ImageIcon vipham=ImageProcess.scaled("../Assets/warning-icon-512x511-3vl9qoze.png", 20, 20);
		violations.setIcon(vipham);
		panel_2_4.add(violations);
		
		
		//khoá tài khoản
		JPanel panel_2_4_1 = new JPanel();
		panel_2_4_1.setLayout(null);
		panel_2_4_1.setBackground(new Color(0, 128, 255));
		panel_2_4_1.setBounds(0, 360, 163, 40);
		nav.add(panel_2_4_1);
		Animation.onHoover(panel_2_4_1, new Color(0,128,255) , new Color(0,128,192));
		
		JLabel lockacc = new JLabel("Khoá tài khoản");
		panel_2_4_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SearchUser su=new SearchUser(LibrarianHP.this,"",1);
				su.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		lockacc.setForeground(Color.WHITE);
		lockacc.setBounds(0, 0, 163, 40);
		ImageIcon banned=ImageProcess.scaled("../Assets/banned.png", 20, 20);
		lockacc.setIcon(banned);
		panel_2_4_1.add(lockacc);
		
		//mục thủ thư
		JLabel librarian = new JLabel("Thủ thư");
		librarian.setForeground(Color.WHITE);
		librarian.setFont(new Font("Tahoma", Font.BOLD, 14));
		librarian.setBackground(Color.LIGHT_GRAY);
		librarian.setBounds(10, 424, 103, 27);
		ImageIcon libra=ImageProcess.scaled("../Assets/librarian.png", 27, 27);
		librarian.setIcon(libra);
		nav.add(librarian);
		
		//đổi mật khẩu
		JPanel panel_2_4_1_1 = new JPanel();
		panel_2_4_1_1.setLayout(null);
		panel_2_4_1_1.setBackground(new Color(0, 128, 255));
		panel_2_4_1_1.setBounds(0, 462, 163, 40);
		nav.add(panel_2_4_1_1);
		Animation.onHoover(panel_2_4_1_1, new Color(0,128,255) , new Color(0,128,192));
		
		JLabel changeP = new JLabel("Đổi mật khẩu");
		panel_2_4_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChangePassword cp=new ChangePassword(l,LibrarianHP.this);
				cp.setVisible(true);
				//setVisible(false);
				//dispose();
			}
		});
		changeP.setForeground(Color.WHITE);
		changeP.setBounds(0, 0, 163, 40);
		ImageIcon change1=ImageProcess.scaled("../Assets/change-password-5.png", 20, 20);
		changeP.setIcon(change1);
		panel_2_4_1_1.add(changeP);
		
		//thống kê
		//số đầu sách
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 128, 64));
		panel_3.setBounds(234, 206, 219, 121);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel bookNo = new JLabel(String.valueOf(Statics.books()));
		bookNo.setForeground(new Color(255, 255, 255));
		bookNo.setFont(new Font("Tahoma", Font.BOLD, 50));
		bookNo.setBounds(26, 11, 151, 84);
		panel_3.add(bookNo);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 0, 0));
		panel_4.setBounds(234, 170, 219, 35);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		JLabel books = new JLabel("Số đầu sách");
		books.setForeground(new Color(255, 255, 255));
		books.setFont(new Font("Tahoma", Font.BOLD, 20));
		books.setBounds(10, 11, 125, 24);
		panel_4.add(books);
		
		//sách mới
		String[] b= {"Mã sách","Tên sách","Nhà xuất bản","Số lượng"};
		DefaultTableModel model=new DefaultTableModel(b,0);
		FetchData.fetchBook(model);
		table = new JTable(model);
	    JScrollPane usp = new JScrollPane(table);
	    usp.setBounds(173, 424, 567, 229);
		getContentPane().add(usp);
		
		JLabel newBook = new JLabel("Sách mới nhất");
		newBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newBook.setBounds(173, 370, 172, 30);
		getContentPane().add(newBook);
		
		
		
		
		//số người dùng
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBackground(new Color(0, 64, 128));
		panel_4_1.setBounds(525, 170, 219, 35);
		getContentPane().add(panel_4_1);
		panel_4_1.setLayout(null);
		
		JLabel users = new JLabel("Số người dùng");
		users.setForeground(new Color(255, 255, 255));
		users.setFont(new Font("Tahoma", Font.BOLD, 20));
		users.setBounds(10, 11, 155, 24);
		panel_4_1.add(users);
		
		JPanel panel_4_2 = new JPanel();
		panel_4_2.setBackground(new Color(128, 128, 255));
		panel_4_2.setBounds(525, 206, 219, 121);
		getContentPane().add(panel_4_2);
		panel_4_2.setLayout(null);
		
		JLabel userNo = new JLabel(String.valueOf(Statics.users()));
		userNo.setForeground(Color.WHITE);
		userNo.setFont(new Font("Tahoma", Font.BOLD, 50));
		userNo.setBounds(10, 11, 151, 84);
		panel_4_2.add(userNo);
		
		
		// số sách đang mượn
		JPanel panel_4_3 = new JPanel();
		panel_4_3.setBackground(new Color(0, 128, 0));
		panel_4_3.setBounds(823, 170, 219, 35);
		getContentPane().add(panel_4_3);
		panel_4_3.setLayout(null);
		
		JLabel borrowing = new JLabel("Số sách đang mượn");
		borrowing.setForeground(new Color(255, 255, 255));
		borrowing.setFont(new Font("Tahoma", Font.BOLD, 20));
		borrowing.setBounds(10, 11, 209, 24);
		panel_4_3.add(borrowing);
		
		JPanel panel_4_4 = new JPanel();
		panel_4_4.setBackground(new Color(0, 255, 0));
		panel_4_4.setBounds(823, 206, 219, 121);
		getContentPane().add(panel_4_4);
		panel_4_4.setLayout(null);
		
		JLabel borrowNo = new JLabel(String.valueOf(Statics.borrowingBook()));
		borrowNo.setForeground(Color.WHITE);
		borrowNo.setFont(new Font("Tahoma", Font.BOLD, 50));
		borrowNo.setBounds(10, 11, 151, 84);
		panel_4_4.add(borrowNo);
		
		
		//số sách quá hạn
		JPanel panel_4_5 = new JPanel();
		panel_4_5.setBackground(new Color(64, 0, 0));
		panel_4_5.setBounds(1118, 170, 219, 35);
		getContentPane().add(panel_4_5);
		panel_4_5.setLayout(null);
		
		JLabel expired = new JLabel("Sách quá hạn ");
		expired.setForeground(new Color(255, 255, 255));
		expired.setFont(new Font("Tahoma", Font.BOLD, 20));
		expired.setBounds(10, 11, 199, 24);
		panel_4_5.add(expired);
		
		JPanel panel_4_6 = new JPanel();
		panel_4_6.setBackground(new Color(128, 0, 0));
		panel_4_6.setBounds(1118, 206, 219, 121);
		getContentPane().add(panel_4_6);
		panel_4_6.setLayout(null);
		
		JLabel expiredNo = new JLabel(String.valueOf(Statics.expiredBook()));
		expiredNo.setForeground(Color.WHITE);
		expiredNo.setFont(new Font("Tahoma", Font.BOLD, 50));
		expiredNo.setBounds(10, 11, 151, 84);
		panel_4_6.add(expiredNo);
		
		
		//bảng người dùng mới
		JLabel newUser = new JLabel("Người dùng mới");
		newUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		newUser.setBounds(794, 370, 172, 30);
		getContentPane().add(newUser);
		
		
		String[] a= {"Mã người dùng","Tên người dùng","Số điện thoại","Tên đăng nhập"};
		DefaultTableModel model1=new DefaultTableModel(a,0);
		FetchData.fetchUser(model1);
		table_1 = new JTable(model1);
		JScrollPane jsp1 = new JScrollPane(table_1);
	    jsp1.setBounds(779, 424, 558, 229);
		getContentPane().add(jsp1);
		
		//tải lại trang
		ref.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bookNo.setText(String.valueOf(Statics.books()));
				userNo.setText(String.valueOf(Statics.users()));
				borrowNo.setText(String.valueOf(Statics.borrowingBook()));
				expiredNo.setText(String.valueOf(Statics.expiredBook()));
				FetchData.fetchBook(model);
				table.setModel(model);
				FetchData.fetchUser(model1);
				table_1.setModel(model1);
			}
		});
	}
}
