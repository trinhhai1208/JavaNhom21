
package frontend.components.librarian;


import javax.swing.JFrame;

import backend.controllers.LibrarianController;
import backend.models.Category;
import backend.utils.FetchBE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class AddBook extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField maSachInp;
	private JTextField tenSachInp;
	private JTextField nxbInp;
	private JTextField priceInp;

	

	/**
	 * Create the frame.
	 */
	public AddBook(JFrame parent) {
		Initialize(parent);
	}
	
	
	
	private static int daysInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

	
	private void Initialize(JFrame parent) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		setTitle("Thêm sách");
		
		JLabel title = new JLabel("Thêm sách\r\n");
		title.setBounds(20, 56, 258, 53);
		title.setForeground(new Color(128, 128, 128));
		title.setFont(new Font("Tahoma", Font.BOLD, 24));
		getContentPane().add(title);
		
		
		//Các trường nhập liệu
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
		nph.setBounds(44, 336, 120, 20);
		nph.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(nph);
		
		maSachInp = new JTextField();
		maSachInp.setBounds(165, 138, 160, 20);
		getContentPane().add(maSachInp);
		maSachInp.setColumns(10);
		
		tenSachInp = new JTextField();
		tenSachInp.setBounds(165, 198, 160, 20);
		tenSachInp.setColumns(10);
		getContentPane().add(tenSachInp);
		
		nxbInp = new JTextField();
		nxbInp.setBounds(165, 271, 160, 20);
		nxbInp.setColumns(10);
		getContentPane().add(nxbInp);
				
		JLabel nam = new JLabel("Năm");
		nam.setBounds(328, 338, 46, 14);
		getContentPane().add(nam);
		
		SpinnerNumberModel model11=new SpinnerNumberModel(2024,1900,2024,1);
		JSpinner yearInp = new JSpinner(model11);
				yearInp.setBounds(357, 335, 51, 20);
		getContentPane().add(yearInp);
		
		JLabel thang = new JLabel("Tháng");
		thang.setBounds(250, 338, 46, 14);
		getContentPane().add(thang);
		
		SpinnerNumberModel model12=new SpinnerNumberModel(5,1,12,1);
		JSpinner thangInp = new JSpinner(model12);
		thangInp.setBounds(286, 335, 39, 20);
		getContentPane().add(thangInp);
		
		JLabel day = new JLabel("Ngày");
		day.setBounds(175, 338, 46, 14);
		getContentPane().add(day);
		
		int year=(int) yearInp.getValue();
		int month=(int)thangInp.getValue();
						
		
		SpinnerNumberModel model13=new SpinnerNumberModel(19,1,daysInMonth(year,month),1);
		JSpinner dayInp = new JSpinner(model13);
		dayInp.setBounds(208, 335, 39, 20);
		getContentPane().add(dayInp);
		
		thangInp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int s_year = (int) yearInp.getValue();
				int s_month=(int)thangInp.getValue();
				int maxday=daysInMonth(s_year,s_month);
				SpinnerNumberModel n_model=new SpinnerNumberModel(19,1,maxday,1);
				dayInp.setModel(n_model);
			}
		});

		
		JLabel cate = new JLabel("Thể loại");
		cate.setFont(new Font("Tahoma", Font.BOLD, 14));
		cate.setBounds(441, 139, 80, 19);
		getContentPane().add(cate);
		
		JLabel price = new JLabel("Giá");
		price.setFont(new Font("Tahoma", Font.BOLD, 14));
		price.setBounds(441, 272, 80, 14);
		getContentPane().add(price);
		
		JLabel sl = new JLabel("Số lượng");
		sl.setFont(new Font("Tahoma", Font.BOLD, 14));
		sl.setBounds(441, 201, 80, 17);
		getContentPane().add(sl);
		
		JComboBox<String> categoryInp = new JComboBox<String>();
		List<Category> cs=FetchBE.fetchCate();
		for(Category c: cs) {
			categoryInp.addItem(c.getTenDanhMuc());
		}
		categoryInp.setBounds(521,137,136,20);
		getContentPane().add(categoryInp);
		
		SpinnerNumberModel model2=new SpinnerNumberModel(1,1,100,1);
		JSpinner slInp = new JSpinner(model2);
		slInp.setBounds(521, 198, 66, 20);
		getContentPane().add(slInp);
		
		priceInp = new JTextField();
		priceInp.setBounds(521, 271, 136, 20);
		getContentPane().add(priceInp);
		priceInp.setColumns(10);
		
		
		//Thêm sách
		JButton submit = new JButton("Thêm sách");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(maSachInp.getText().equals("")||tenSachInp.getText().equals("")||nxbInp.getText().equals("")||priceInp.getText().equals("")) {
					JOptionPane.showMessageDialog(AddBook.this,"Vui lòng nhập đầy đủ thông tin");
					return;
				}
				
				try {
		            double priceValue = Double.parseDouble(priceInp.getText());
		            if (priceValue <= 0) {
		                JOptionPane.showMessageDialog(AddBook.this, "Giá không hợp lệ");
		                return;
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(AddBook.this, "Giá không hợp lệ");
		            return;
		        }
				
				LibrarianController.addBook(maSachInp.getText(),tenSachInp.getText(),nxbInp.getText(),year,month,(Integer)dayInp.getValue(),(String)categoryInp.getSelectedItem(),(Integer)slInp.getValue(),Double.parseDouble(priceInp.getText()));
				JOptionPane.showMessageDialog(AddBook.this, "Thêm sách thành công");
			}
		});
		submit.setBounds(486, 386, 101, 23);
		getContentPane().add(submit);
		
		//Reset dữ liệu đã nhập
		JButton huy = new JButton("Huỷ");
		huy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maSachInp.setText("");
				tenSachInp.setText("");
				nxbInp.setText("");
				priceInp.setText("");
				categoryInp.setSelectedItem("Giáo trình");
				dayInp.setValue(1);
				thangInp.setValue(5);
				yearInp.setValue(2024);
				slInp.setValue(1);
				
			}
		});
		huy.setBounds(607, 386, 89, 23);
		getContentPane().add(huy);
		
		//Quay lại trang trước đó
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
