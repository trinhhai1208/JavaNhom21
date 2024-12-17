package backend.utils;

import java.util.ArrayList;
import java.util.List;

import backend.models.Account;
import backend.models.Book;
import backend.models.BorrowSlip;
import backend.models.Category;
import backend.models.Violation;

public class FetchBE {
	static List<Book> books=new ArrayList<>();
	static List<Account> accounts=new ArrayList<>();
	static List<BorrowSlip> bs=new ArrayList<>(); 
	static List<Violation> violations=new ArrayList<>();
	
	//lấy tt sách theo từ khoá
	public static List<Book> findB(String keyword){
		books=ReadData.readBook("../DemoDB/Book.txt");
		List<Book> result=new ArrayList<>();
		for(Book b : books) {
			if(b.getTenSach().toLowerCase().contains(keyword.toLowerCase())) {
				result.add(b);
			}
		}
		return result;
	}
	
	//lấy tt người dùng theo từ khoá
	public static List<Account> findA(String keyword){
		accounts=ReadData.readAccount("../DemoDB/user-account.txt");
		List<Account> result=new ArrayList<>();
		for(Account a : accounts) {
			if(a.getTenDangNhap().toLowerCase().contains(keyword.toLowerCase())) {
				result.add(a);
			}
		}
		return result;
	}
	
	//lấy tt phiếu mượn theo từ khoá
	public static List<BorrowSlip> findBS(String keyword){
		bs=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		List<BorrowSlip> result=new ArrayList<>();
		for(BorrowSlip s : bs) {
			if(s.getMaPhieuMuon().toLowerCase().equals(keyword)) {
				result.add(s);
			}
		}
		return result;
	}
	
	//lấy toàn bộ phiếu vi phạm
	public static List<Violation> fetchViolation(){
		violations= ReadData.readViolation("../DemoDB/Violation.txt");
		return violations;
	}
	public static boolean findV(String maPhieu,String maUser) {
		bs=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		for(BorrowSlip s : bs) {
			if(s.getMaPhieuMuon().equals(maPhieu)&&s.getMaTaiKhoan().equals(maUser)) {
				return true;
			}
		}
		return false;
	}
	
	//lấy số lượng phiếu vi phạm của 1 người dùng
	public static int countV(String maTaiKhoan) {
		violations=ReadData.readViolation("../DemoDB/Violation.txt");
		int count=0;
		for(Violation v : violations) {
			if(v.getMaTaiKhoan().equals(maTaiKhoan))count++;
		}
		return count;
	}
	
	//lấy tt phiếu mượn chưa được duyệt và phiếu mượn chưa trả
	public static List<BorrowSlip> findBSbyStatus(){
		bs=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		List<BorrowSlip> result=new ArrayList<>();
		for(BorrowSlip s : bs) {
			if("Pending".equals(s.getTrangThai())||"Approved".equals(s.getTrangThai())) {
				result.add(s);
			}
		}
		return result;
	}
	
	//lấy tt danh mục
	public static List<Category> fetchCate() {
		return ReadData.readCategory("../DemoDB/Category.txt");
	}
	
	//lấy tt phiếu mượn vi phạm
	public static List<BorrowSlip> findViolatedBS(){
		bs=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		List<BorrowSlip> result=new ArrayList<>();
		for(BorrowSlip s: bs) {
			if((s.getTrangThai().equals("Lost")||s.getTrangThai().equals("Expired"))) {
				result.add(s);
			}
		}
		return result;
	}

}
