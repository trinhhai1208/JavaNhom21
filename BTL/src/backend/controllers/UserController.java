package backend.controllers;

import backend.models.Account;
import backend.models.Book;
import backend.models.BorrowSlip;
import backend.utils.ReadData;
import backend.utils.SessionManager;
import backend.utils.WriteData;
import frontend.components.user.BorrowBook;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    // đăng nhập user
    public static Account login(String username, String password) {
        List<Account> users = ReadData.readAccount("../DemoDB/user-account.txt");
        for (Account a : users) {
            if (a.getTenDangNhap().equals(username) && a.getMatKhau().equals(password)) {
                return a;
            }
        }
        return null;
    }

    // đổi mật khẩu
    public static boolean changePassword(Account currentUser, String newPassword) {
        currentUser.setMatKhau(newPassword); // Assuming Account has a setPassword method
        SessionManager.login(currentUser); // Update the session
        List<Account> accounts = ReadData.readAccount("../DemoDB/user-account.txt");
        // Find and update the current user's information
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getMaTaiKhoan().equals(currentUser.getMaTaiKhoan())) {
                accounts.set(i, currentUser);
                break;
            }
        }
        WriteData.writeAccount(accounts, "../DemoDB/user-account.txt");
        return true;
    }

    // Sửa thông tin cá nhân
    public static boolean editPersonalInfo(Account currentUser){
        SessionManager.login(currentUser);
        List<Account> accounts = ReadData.readAccount("../DemoDB/user-account.txt");
        for (int i = 0; i < accounts.size(); i++) {
            if (currentUser.getMaTaiKhoan().equals(accounts.get(i).getMaTaiKhoan())) {
                accounts.set(i, currentUser);
                break;
            }
        }

        WriteData.writeAccount(accounts, "../DemoDB/user-account.txt");
        return true;
    }

    // Tìm kiếm sách
    public static List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ReadData.f_path("../DemoDB/Book.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                //line = line.substring(1, line.length() - 1);
                String[] parts = line.split("\\|");
                if (parts.length >= 8) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    String author = parts[2].trim();
                    String releaseDate = parts[3].trim();
                    String category = parts[4].trim();
                    int quantity = Integer.parseInt(parts[5].trim());
                    double price = Double.parseDouble(parts[6].trim());
                    String status=parts[7].trim();
                    if(status.equals("true")) {
                        if (name.toLowerCase().contains(keyword.toLowerCase()) || code.toLowerCase().contains(keyword.toLowerCase())) {
                            books.add(new Book(code, name, author, LocalDate.parse(releaseDate), category, quantity, price));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return books;
    }

    // Đặt sách mượn
    public static List<BorrowSlip> getBorrowedBooks(String userId) {
        List<BorrowSlip> borrowSlips = new ArrayList<>();
        try (BufferedReader brSlip = new BufferedReader(new FileReader(ReadData.f_path("../DemoDB/borrow-slip.txt")))) {
            String line;
            while ((line = brSlip.readLine()) != null) {
                line = line.substring(1, line.length() - 1);
                String[] parts = line.split("\\|");
                if (parts.length >= 6 && parts[3].trim().equals(userId) && (!"Dissaprove".equals(parts[5].trim()) && !"Pending".equals(parts[5].trim()))) {
                    String maPhieuMuon = parts[0].trim();
                    String ngayMuon = parts[1].trim();
                    String maNguoiDung = parts[3].trim();
                    String maSach = parts[4].trim();
                    String trangThai = parts[5].trim();

                    borrowSlips.add(new BorrowSlip(maPhieuMuon, LocalDate.parse(ngayMuon), maNguoiDung, maSach, trangThai));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return borrowSlips;
    }


}
