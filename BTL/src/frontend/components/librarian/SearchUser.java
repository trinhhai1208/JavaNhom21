package frontend.components.librarian;


import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import backend.controllers.LibrarianController;
import backend.utils.FetchBE;
import frontend.utils.FetchData;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Color;

public class SearchUser extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField searchBar;
	private JTable table;
	private JLabel title;


	/**
	 * Create the application.
	 */
	
	public SearchUser(JFrame parent,String keyword,int searchFor) {
		initialize(parent,keyword,searchFor);
	}

		
	/**
	 * @wbp.parser.constructor
	 */
	public SearchUser(JFrame parent,String keyword) {
		initialize(parent,keyword);
	}

	
	private void initialize(JFrame parent,String keyword) {
		setTitle("Tìm kiếm");
		Rectangle r=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(0,0,r.width,r.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		//thanh tìm kiếm
		searchBar=new JTextField();
		searchBar.setText(keyword);
		searchBar.setBounds(951, 21, 276, 35);
		searchBar.setColumns(10);
		getContentPane().add(searchBar);
		
		JButton search = new JButton("Tìm kiếm");
		search.setBackground(new Color(0, 128, 255));
		search.setBounds(1249, 20, 89, 30);
		getContentPane().add(search);
		
		
		//tạo bảng
		String[] u= {"Mã tài khoản ","Tên người dùng","Địa chỉ","Số điện thoại","Tên đăng nhập","Mật khẩu"};
		DefaultTableModel model=new DefaultTableModel(u,0);
		FetchData.fetchUser(keyword,model);
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FetchData.fetchUser(searchBar.getText(),model);
			}
		});
				
		table = new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		
		sp.setBounds(39, 117, 1257, 541);
		getContentPane().add(sp);
		
		//Quay lại trang trước đó
		JButton ql = new JButton("Quay lại");
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
				parent.setVisible(true);
			}
		});
		ql.setBounds(42, 20, 89, 23);
		getContentPane().add(ql);
		
		
		title = new JLabel("TÌM KIẾM NGƯỜI DÙNG");
		title.setForeground(Color.GRAY);
		title.setFont(new Font("Tahoma", Font.BOLD, 26));
		title.setBounds(300, 33, 373, 35);
		getContentPane().add(title);
	}
	
	//khoá người dùng
	//searchFor=1 equal to disable user
	private void initialize(JFrame parent,String keyword,int searchFor) {
		setTitle("Tìm kiếm");
		Rectangle r=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(0,0,r.width,r.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		//thanh tìm kiếm
		searchBar=new JTextField();
		searchBar.setText(keyword);
		searchBar.setBounds(951, 21, 276, 35);
		searchBar.setColumns(10);
		getContentPane().add(searchBar);
		
		JButton search = new JButton("Tìm kiếm");
		search.setBackground(new Color(0, 128, 255));
		search.setBounds(1249, 20, 89, 30);
		getContentPane().add(search);
		
		//tạo bảng
		String[] u= {"Mã tài khoản ","Tên người dùng","Địa chỉ","Số điện thoại","Tên đăng nhập","Mật khẩu","Trạng thái"};
		DefaultTableModel model=new DefaultTableModel(u,0);
		FetchData.fetchUser(keyword,model);
		
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FetchData.fetchUser(searchBar.getText(),model);
			}
		});
				
		table = new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		
		sp.setBounds(39, 117, 1257, 541);
		getContentPane().add(sp);
		
		//quay lại trang trước đó
		JButton ql = new JButton("Quay lại");
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
				parent.setVisible(true);
			}
		});
		ql.setBounds(42, 20, 89, 23);
		getContentPane().add(ql);
				
		//khoá người dùng
		switch(searchFor) {
		case 1:
			setTitle("Khoá người dùng");
			title=new JLabel("Khoá người dùng");
			title.setForeground(Color.GRAY);
			title.setFont(new Font("Tahoma", Font.BOLD, 26));
			title.setBounds(300, 33, 373, 35);
			getContentPane().add(title);
			//sửa bảng
			model.addColumn("Số lần vi phạm");
			for (int i = 0; i < model.getRowCount(); i++) {
	            model.setValueAt(FetchBE.countV(model.getValueAt(i, 0).toString()), i, model.getColumnCount() - 1);
	        }
			//nhấn vào dòng trong bảng
			table.addMouseListener(new MouseAdapter() {
				@Override 
				public void mouseClicked(MouseEvent e) {
					int selected=table.getSelectedRow();
					System.out.println(selected);
					if(table.getValueAt(selected, 6).toString().equals("Active")) {
						int confirm=JOptionPane.showConfirmDialog(SearchUser.this,"Bạn có chắc chắn khoá tài khoản người dùng này");
						if(confirm==JOptionPane.YES_OPTION) {
							table.setValueAt("Inactive",selected, 6);
							if(LibrarianController.changeAccStatus(table.getValueAt(selected, 0).toString())) {
								JOptionPane.showMessageDialog(SearchUser.this,"Khoá tài khoản thành công");
							}
							else JOptionPane.showMessageDialog(SearchUser.this,"Lỗi kết nối");
						}
					}
					else {
						if(table.getValueAt(selected, 6).toString().equals("Inactive")) {
							int confirm=JOptionPane.showConfirmDialog(SearchUser.this,"Bạn có chắc chắn muốn mở khoá tài khoản người dùng này");
							if(confirm==JOptionPane.YES_OPTION) {
								table.setValueAt("Active",selected, 6);
								if(LibrarianController.changeAccStatus(table.getValueAt(selected, 0).toString())) {
									JOptionPane.showMessageDialog(SearchUser.this,"Mở khoá tài khoản thành công");
								}
								else JOptionPane.showMessageDialog(SearchUser.this,"Lỗi kết nối");
							}
						}
					}
				}
			});
			break;
		}
	}

}
