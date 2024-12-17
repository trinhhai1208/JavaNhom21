
package backend.controllers;
import backend.models.Account;
import backend.models.Book;
import backend.models.BorrowSlip;
import backend.utils.ReadData;
import backend.utils.WriteData;
import backend.models.Librarian;
import backend.models.Violation;

import java.util.Iterator;
import java.util.List;
import java.time.LocalDate;
public class LibrarianController {
	    public static List<Book> books;	    
	    public static List<Librarian> librarians;
	    public static List<Account> accounts;
	    public static List<Violation> violations;
	    public static List<BorrowSlip> borrowSlips;
	    
	    //đăng nhập thủ thư
		public static Librarian login(String l_accountName,String l_password) {
		librarians=ReadData.readLibrarian("../DemoDB/Librarian.txt");
		for(Librarian l : librarians) {
			if(l_accountName.equals(l.getAccountName()) && l_password.equals(l.getPassword())) {
				return l;
			}
		}
		return null;
	    }
		
		//đổi mật khẩu (thủ thư)
		public static boolean changePassword(Librarian l,String n_password) {
			librarians=ReadData.readLibrarian("../DemoDB/Librarian.txt");
			for(Librarian ls : librarians) {
				if(l.getAccountName().equals(ls.getAccountName())) {
					ls.setPassword(n_password);
				}
			}
			WriteData.writeLibrarian(librarians,"../DemoDB/Librarian.txt");
			return true;
		}
		
		
		//thêm sách
	    public static boolean addBook(String maSach, String tenSach,String nxb, int nam,int thang,int ngay, String theLoai, int soLuong, double gia) {
	     LocalDate nph = LocalDate.of(nam,thang,ngay);
	     books=ReadData.readBook("../DemoDB/Book.txt");
		 boolean bookFound = false;
	        for (Book book : books) {
	            if (book.getMaSach().equals(maSach)) {
	                bookFound = true;
	                book.setSl(book.getSl() + soLuong); 
	                break;
	            }
	        }
	        if (!bookFound) {
				books.add(new Book(maSach, tenSach, nxb, nph, theLoai, soLuong, gia,true));
	        }
	    WriteData.writeBook(books,"../DemoDB/Book.txt");
	    return true;
	    }
	    
	    //xoá sách
		public static boolean delBook(String maSach) {
			 books=ReadData.readBook("../DemoDB/Book.txt");
			 boolean bookFound = false;
			 Iterator<Book> iterator = books.iterator();
			    while (iterator.hasNext()) {
			        Book book = iterator.next();
			        if (book.getMaSach().equals(maSach)) {
			            iterator.remove();
			            bookFound = true;
			        }
			    }
			 if(bookFound) {
				 WriteData.writeBook(books,"../DemoDB/Book.txt");
				 return true;
			 }
		     return false;
		}
		
		//sửa thông tin sách
		public static boolean editBook(String maSach,String n_masach,String n_tenSach, String n_NXB, int year,int month,int day, String n_theLoai,int n_sl, double n_gia) {
			LocalDate nph = LocalDate.of(year,month,day);
			books=ReadData.readBook("../DemoDB/Book.txt");
			boolean bookFound = false;
			for (Book book : books) {
		        if (book.getMaSach().equals(maSach)) {
		            book.setMaSach(n_masach);
		            book.setTenSach(n_tenSach);
		            book.setNXB(n_NXB);
		            book.setNph(nph);
		            book.setTheLoai(n_theLoai);
		            book.setSl(n_sl);
		            book.setGia(n_gia);
		            bookFound = true;
		            break; // Break the loop once the book is found and updated
		        }
			}
		    if (bookFound) {
		    	WriteData.writeBook(books, "../DemoDB/Book.txt");
		    	return true;
		    }
			return false;
	}
		
		// khoá/mở khoá tài khoản
		public static boolean changeAccStatus(String maTaiKhoan) {
			accounts=ReadData.readAccount("../DemoDB/user-account.txt");
			for(Account a : accounts) {
				if(a.getMaTaiKhoan().equals(maTaiKhoan)) {
					if(a.getIsActive())a.setIsActive(false);
					else a.setIsActive(true);
				}
			}
			WriteData.writeAccount(accounts,"../DemoDB/user-account.txt");
			return true;
		}
		
		//tạo phiếu vi phạm
		public static boolean addViolation(String maPhieuMuon,String maTaiKhoan,String lydo,int soNgayViPham,String soTienPhat) {
			 borrowSlips=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		     violations=ReadData.readViolation("../DemoDB/Violation.txt");
		     double stp=Double.parseDouble(soTienPhat);
		        for(BorrowSlip s: borrowSlips) {
		        	if(s.getMaPhieuMuon().equals(maPhieuMuon)) {
		        		if(s.getMaTaiKhoan().equals(maTaiKhoan)) {
		        			violations.add(new Violation(maPhieuMuon, maTaiKhoan, lydo, soNgayViPham, stp));
		        		    WriteData.writeViolation(violations,"../DemoDB/Violation.txt");
		        		    s.setTrangThai("Solved");
		        		    WriteData.writeBorrowSlip(borrowSlips,"../DemoDB/borrow-slip.txt");
		        		    return true;
		        		}
		        	}
		        }
		        return false;
		    }
		
		//phê duyệt mượn sách
		public static boolean approveBorrowSlip(String maPhieuMuon,String status){
			borrowSlips=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
			books=ReadData.readBook("../DemoDB/book.txt");
			for(BorrowSlip bs: borrowSlips) {
				if(bs.getMaPhieuMuon().equals(maPhieuMuon)) {
					bs.setTrangThai(status);
					if(bs.getTrangThai().equals("Approved")) {
						for(Book b : books) {
							if(bs.getMaSach().equals(b.getMaSach())) {
								b.reduceQuantity(1);
								if(b.getSl()==0) {
									b.setAvaiable(false);
								}
							}
						}
					}
					break;
				}
			}
			WriteData.writeBook(books, "../DemoDB/book.txt");
			WriteData.writeBorrowSlip(borrowSlips, "../DemoDB/borrow-slip.txt");
			return true;
	}
		
	//phê duyệt trả sách
	public static boolean returnBook(String maPhieuMuon,String status) {
		borrowSlips=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		books=ReadData.readBook("../DemoDB/book.txt");
		for(BorrowSlip bs: borrowSlips) {
			if(bs.getMaPhieuMuon().equals(maPhieuMuon)) {
				if(status.equals("Mất sách")) {
					bs.setTrangThai("Lost");
					WriteData.writeBorrowSlip(borrowSlips, "../DemoDB/borrow-slip.txt");
					return true;
				}
				if(bs.getTrangThai().equals("Approved")&&bs.getNgayTra().isBefore(LocalDate.now())) {
					bs.setTrangThai("Expired");
				}
				else if((bs.getNgayTra().isAfter(LocalDate.now())||bs.getNgayTra().isEqual(LocalDate.now()))&&bs.getTrangThai().equals("Approved")) {
					bs.setTrangThai("onTime");
				}
				
				for(Book b : books) {
					if(bs.getMaSach().equals(b.getMaSach())) {
						b.increaseQuantity(1);
					}
				}
			break;
			}
		}
		WriteData.writeBook(books, "../DemoDB/book.txt");
		WriteData.writeBorrowSlip(borrowSlips, "../DemoDB/borrow-slip.txt");
		return true;
	}
}
