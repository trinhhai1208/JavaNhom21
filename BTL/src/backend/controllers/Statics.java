package backend.controllers;

import backend.utils.ReadData;

import java.time.LocalDate;
import java.util.List;
import backend.models.BorrowSlip;

public class Statics {
	//số sách
	public static int books() {
		return ReadData.readBook("../DemoDB/Book.txt").size();
		
	}
	//số người dùng
	public static int users() {
		return ReadData.readAccount("../DemoDB/user-account.txt").size();
	}

	//số sang đang được mượn
	public static int borrowingBook() {
		List<BorrowSlip> bs=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		int count=0;
		for(BorrowSlip s: bs) {
			if(s.getTrangThai().equals("Approved")) {
				count++;
			}
		}
		return count;
	}
	//số sách hết hạn
	public static int expiredBook() {
		List<BorrowSlip> bs=ReadData.readBorrowSlip("../DemoDB/borrow-slip.txt");
		int count=0;
		for(BorrowSlip s: bs) {
			if(s.getNgayTra().isBefore(LocalDate.now())&&s.getTrangThai().equals("Approved")) {
				count++;
			}
		}
		return count;
	}
}
