package frontend.components.librarian;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.controllers.LibrarianController;
import backend.models.Book;
import backend.models.Category;
import backend.utils.FetchBE;


public class EditBook extends JFrame{
	private JTextField maSachInp;
	private JTextField textField_1;
	private JTextField nxbInp;
	private JTextField priceInp;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the application.
	 */
	public EditBook(JFrame parent,Book b) {
		initialize(parent,b);
	}
	//tính ngày trong tháng
	private static int daysInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame parent,Book b) {
		
		setBounds(100, 100, 800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Sửa thông tin sách");
		
		JLabel title = new JLabel("Sửa thông tin sách\r\n"+" "+b.getMaSach());
		title.setBounds(20, 56, 401, 53);
		title.setForeground(new Color(128, 128, 128));
		title.setFont(new Font("Tahoma", Font.BOLD, 24));
		getContentPane().add(title);
		
		
		//form sửa thông tin
		JLabel maSach = new JLabel("Mã sách");
		maSach.setBounds(44, 139, 80, 14);
		maSach.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(maSach);
		
		JLabel tenSach = new JLabel("Tên sách");
		tenSach.setBounds(44, 199, 80, 14);
		tenSach.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(tenSach);
		
		JLabel nxb = new JLabel("Nhà xuất bản");
		nxb.setBounds(44, 272, 111, 14);
		nxb.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(nxb);
		
		JLabel nph = new JLabel("Ngày phát hành");
		nph.setBounds(44, 336, 111, 14);
		nph.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(nph);
		
		maSachInp = new JTextField();
		maSachInp.setBounds(165, 138, 160, 20);
		getContentPane().add(maSachInp);
		maSachInp.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(165, 198, 160, 20);
		textField_1.setColumns(10);
		getContentPane().add(textField_1);
		
		nxbInp = new JTextField();
		nxbInp.setBounds(165, 271, 160, 20);
		nxbInp.setColumns(10);
		getContentPane().add(nxbInp);
		
		
		int day=b.getNph().getDayOfMonth();
		int month=b.getNph().getMonthValue();
		int year=b.getNph().getYear();
		JLabel nam = new JLabel("Năm");
		nam.setBounds(328, 338, 46, 14);
		getContentPane().add(nam);
		
		SpinnerNumberModel model11=new SpinnerNumberModel(year,1900,2024,1);
		JSpinner namInp = new JSpinner(model11);
		namInp.setBounds(357, 335, 51, 20);
		getContentPane().add(namInp);
		
		JLabel thang = new JLabel("Tháng");
		thang.setBounds(250, 338, 46, 14);
		getContentPane().add(thang);
		
		SpinnerNumberModel model12=new SpinnerNumberModel(month,1,12,1);
		JSpinner thangInp = new JSpinner(model12);
		thangInp.setBounds(286, 335, 39, 20);
		getContentPane().add(thangInp);
		
		JLabel ngay = new JLabel("Ngày");
		ngay.setBounds(175, 338, 46, 14);
		getContentPane().add(ngay);
						
		
		SpinnerNumberModel model13=new SpinnerNumberModel(19,1,daysInMonth(year,month),1);
		JSpinner ngayInp = new JSpinner(model13);
		ngayInp.setBounds(208, 335, 39, 20);
		getContentPane().add(ngayInp);
		
		thangInp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int s_year = (int) namInp.getValue();
				int s_month=(int)thangInp.getValue();
				int maxday=daysInMonth(s_year,s_month);
				SpinnerNumberModel n_model=new SpinnerNumberModel(19,1,maxday,1);
				ngayInp.setModel(n_model);
			}
		});
		
		JLabel cate = new JLabel("Thể loại");
		cate.setFont(new Font("Tahoma", Font.BOLD, 14));
		cate.setBounds(441, 139, 80, 19);
		getContentPane().add(cate);
		
		JLabel price = new JLabel("Giá");
		price.setFont(new Font("Tahoma", Font.BOLD, 14));
		price.setBounds(441, 274, 80, 14);
		getContentPane().add(price);
		
		JLabel sl = new JLabel("Số lượng");
		sl.setFont(new Font("Tahoma", Font.BOLD, 14));
		sl.setBounds(441, 201, 80, 17);
		getContentPane().add(sl);
		
		JComboBox<String> categoriesInp = new JComboBox<String>();
		List<Category> cs=FetchBE.fetchCate();
		for(Category c: cs) {
			categoriesInp.addItem(c.getTenDanhMuc());
		}
		categoriesInp.setBounds(521,137,136,20);
		getContentPane().add(categoriesInp);
		
		SpinnerNumberModel model2=new SpinnerNumberModel(1,1,100,1);
		JSpinner slInp = new JSpinner(model2);
		slInp.setBounds(521, 198, 66, 20);
		getContentPane().add(slInp);
		
		priceInp = new JTextField();
		priceInp.setBounds(521, 271, 136, 20);
		getContentPane().add(priceInp);
		priceInp.setColumns(10);
		
		maSachInp.setText(b.getMaSach());
		String oldMaSach=b.getMaSach();
		textField_1.setText(b.getTenSach());
		nxbInp.setText(b.getNXB());
		priceInp.setText(String.valueOf(b.getGia()));
		categoriesInp.setSelectedItem(b.getTheLoai());
		ngayInp.setValue(day);
		thangInp.setValue(month);
		namInp.setValue(year);
		slInp.setValue(b.getSl());
		
		
		//sửa thông tin
		JButton submit = new JButton("Lưu");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            double priceValue = Double.parseDouble(priceInp.getText());
		            if (priceValue <= 0) {
		                JOptionPane.showMessageDialog(EditBook.this, "Giá  không hợp lệ");
		                return;
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(EditBook.this, "Giá không hợp lệ");
		            return;
		        }
				if(maSachInp.getText().equals("")||textField_1.getText().equals("")||nxbInp.getText().equals("")||priceInp.getText().equals("")) {
					JOptionPane.showMessageDialog(EditBook.this,"Vui lòng nhập đầy đủ thông tin");
				}
				else {
					if(LibrarianController.editBook(oldMaSach,maSachInp.getText(),textField_1.getText(),nxbInp.getText(),year,month,(Integer)ngayInp.getValue(),(String)categoriesInp.getSelectedItem(),(Integer)slInp.getValue(),Double.parseDouble(priceInp.getText()))) {
						JOptionPane.showMessageDialog(EditBook.this, "Sửa thông tin sách thành công");
					}
					else JOptionPane.showMessageDialog(EditBook.this, "Lỗi không xác định");
				}
			}
		});
		submit.setBounds(486, 386, 89, 23);
		getContentPane().add(submit);
		
		//reset thông tin
		JButton huy = new JButton("Huỷ");
		huy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maSachInp.setText(b.getMaSach());
				textField_1.setText(b.getTenSach());
				nxbInp.setText(b.getNXB());
				priceInp.setText(String.valueOf(b.getGia()));
				categoriesInp.setSelectedItem(b.getTheLoai());
				ngayInp.setValue(day);
				thangInp.setValue(month);
				namInp.setValue(year);
				slInp.setValue(b.getSl());
				
			}
		});
		huy.setBounds(607, 386, 89, 23);
		getContentPane().add(huy);
		
		//Quay lại trang trước đo
		JButton ql = new JButton("Quay lại");
		ql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				parent.setVisible(true);
			}
		});
		ql.setBounds(35, 11, 89, 23);
		getContentPane().add(ql);
	}

}
