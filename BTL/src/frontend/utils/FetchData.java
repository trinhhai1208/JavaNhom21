package frontend.utils;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import backend.models.Account;
import backend.models.Book;
import backend.utils.FetchBE;

public class FetchData {
	//lấy tt người dùng theo từ khoá và định dạng bảng 
	public static void fetchUser(String keyword,DefaultTableModel m) {
		m.setRowCount(0);
		List<Account> result=FetchBE.findA(keyword);
		for (Account a : result) {
			String isActive;
			if(a.getIsActive())isActive="Active";
			else isActive="Inactive";
			Object[] row= {a.getMaTaiKhoan(),a.getTenNguoiDung(),a.getDiaChi(),a.getSoDienThoai(),a.getTenDangNhap(),a.getMatKhau(),isActive};
			m.addRow(row);
		}
	}
	
	//lấy toàn bộ người dùng và định dạng bảng
	public static void fetchUser(DefaultTableModel m) {
		m.setRowCount(0);
		List<Account> result=FetchBE.findA("");
		int end = Math.min(10, result.size());
		for (int i = result.size()-1; i >=result.size()-end; i--) {
		    Account a = result.get(i);
		    Object[] row = {a.getMaTaiKhoan(), a.getTenNguoiDung(), a.getSoDienThoai(), a.getTenDangNhap()};
		    m.addRow(row);
		}
	}
	
	//lấy tt sach theo từ khoá và định dạng bảng 
	public static void fetchBook(String keyword,DefaultTableModel m) {
		m.setRowCount(0);
		List<Book> result=FetchBE.findB(keyword);
		
		for (Book b : result) {
			Object[] row= {b.getMaSach(),b.getTenSach(),b.getNXB(),b.getNph(),b.getTheLoai(),b.getSl(),b.getGia()};
			m.addRow(row);
		}
	}
	//lấy toàn bộ sách và định dạng bảng
	public static void fetchBook(DefaultTableModel m) {
		m.setRowCount(0);
		List<Book> result = FetchBE.findB("");
		int end = Math.min(10, result.size());
		for (int i = result.size()-1; i >=result.size()-end; i--) {
		    Book b = result.get(i);
		    Object[] row = {b.getMaSach(), b.getTenSach(), b.getNXB(), b.getSl()};
		    m.addRow(row);
		}
	}
}
