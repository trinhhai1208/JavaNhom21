package frontend.components.librarian;


import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import backend.controllers.LibrarianController;
import backend.models.Book;
import frontend.utils.FetchData;

import java.time.LocalDate;
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

public class SearchBook extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField searchBar;
	private JTable table;
	private JButton ql;
	private JLabel title;


	/**
	 * Create the application.
	 */
	
	
	public SearchBook(JFrame parent,String keyword) {
		initialize(parent,keyword);
	}
	/**
	 * @wbp.parser.constructor
	 */
	public SearchBook(JFrame parent,int searchFor) {
		initialize(parent,searchFor);
	}


	/**
	 * Initialize the contents of the frame.
	 */
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
		
		JButton btnNewButton = new JButton("Tìm kiếm");
		btnNewButton.setBackground(new Color(0, 128, 255));
		btnNewButton.setBounds(1249, 20, 89, 30);
		getContentPane().add(btnNewButton);

		//kết quả tìm kiếm
		String[] b= {"Mã sách","Tên sách","Nhà xuất bản","Ngày phát hành","Thể loại","Số lượng","Giá"};
		DefaultTableModel model=new DefaultTableModel(b,0);
		FetchData.fetchBook(keyword,model);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					FetchData.fetchBook(searchBar.getText(),model);
			}
		});

		
		table = new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		
		sp.setBounds(39, 117, 1257, 597);
		getContentPane().add(sp);
		
		//quay lại trang trước
		ql = new JButton("Quay lại");
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
		
		
		title = new JLabel("TÌM KIẾM SÁCH");
		title.setForeground(Color.GRAY);
		title.setFont(new Font("Tahoma", Font.BOLD, 26));
		title.setBounds(468, 33, 269, 35);
		getContentPane().add(title);
	}
	
	//tìm kiếm để thực hiện các hành động
	//searchFor=2(edit),searchFor=3(delete)
	private void initialize(JFrame parent,int searchFor) {
		setTitle("Tìm kiếm");
		Rectangle r=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setBounds(0,0,r.width,r.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		//thanh tìm kiếm
		searchBar=new JTextField();
		searchBar.setBounds(951, 21, 276, 35);
		searchBar.setColumns(10);
		getContentPane().add(searchBar);
		JButton search = new JButton("Tìm kiếm");
		search.setBackground(new Color(0, 128, 255));
		search.setBounds(1249, 20, 89, 30);
		getContentPane().add(search);
		
		//bảng kết quả
		String[] b= {"Mã sách","Tên sách","Nhà xuất bản","Ngày phát hành","Thể loại","Số lượng","Giá"};
		DefaultTableModel model=new DefaultTableModel(b,0);
		FetchData.fetchBook(searchBar.getText(),model);
		
		table = new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(39, 117, 1257, 533);
		getContentPane().add(sp);
		
		//nhấn nút tìm kiếm
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FetchData.fetchBook(searchBar.getText(),model);
				if(model.getRowCount()==0) {
					JOptionPane.showMessageDialog(SearchBook.this,"Không tìm thấy sách");
					}
			}
		});
		
		//quay lại trang trước đó
		ql = new JButton("Quay lại");
		ql.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
				parent.setVisible(true);
			}
		});
		ql.setBounds(42, 20, 112, 35);
		getContentPane().add(ql);
		
		
		title = new JLabel();
		title.setForeground(Color.GRAY);
		title.setFont(new Font("Tahoma", Font.BOLD, 26));
		title.setBounds(206, 33, 695, 35);
		getContentPane().add(title);
		switch(searchFor) {
		case 2:
			title.setText("TÌM KIẾM SÁCH CẦN SỬA");
			table.addMouseListener(new MouseAdapter() {
				@Override 
				
				//lấy thông tin sách cần sửa
				public void mouseClicked(MouseEvent e) {
					int selected=table.getSelectedRow();
					String maSach=table.getValueAt(selected, 0).toString();
					String tenSach=table.getValueAt(selected, 1).toString();
					String nxb=table.getValueAt(selected, 2).toString();
					LocalDate nph=LocalDate.parse(table.getValueAt(selected, 3).toString());
					String theLoai=table.getValueAt(selected, 4).toString();
					int sl =(Integer)table.getValueAt(selected, 5);
					double gia=(Double)table.getValueAt(selected, 6);
					Book b=new Book(maSach,tenSach,nxb,nph,theLoai,sl,gia);
					EditBook eb=new EditBook(SearchBook.this,b);
					eb.setVisible(true);
					//setVisible(false);
					//dispose();
				}
			});
			break;
		case 3:
			title.setText("TÌM KIẾM SÁCH CẦN XOÁ");
			//xoá sách
			table.addMouseListener(new MouseAdapter() {
				@Override 
				public void mouseClicked(MouseEvent e) {
					int option=JOptionPane.showConfirmDialog(SearchBook.this, "Bạn có chắc chắn muốn xoá");
					if(option==JOptionPane.YES_OPTION) {
						int selected=table.getSelectedRow();
						String maSach=table.getValueAt(selected, 0).toString();
						if(LibrarianController.delBook(maSach)) {
							JOptionPane.showMessageDialog(SearchBook.this, "Xoá thành công,vui lòng quay trở lại");
						}
					}
				}
			});
			break;
		}
	}

}
