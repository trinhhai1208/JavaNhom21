package backend.utils;

import backend.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
	
	//chuyển đổi đường dẫn.Ex: fileName="/BTL_JAVA_IT60195_NHOM9/src/backend/DemoDB/Librarian.txt"
	public static String f_path(String fileName) {
		String projectDirectory = System.getProperty("user.dir");
        File file=new File(projectDirectory,fileName);
        return file.getAbsolutePath();
	}
	
	//đọc dữ liệu
    public static List<Account> readAccount(String fileName) {
        List<Account> accounts = new ArrayList<>();
        String path=f_path(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
               // line = line.substring(1, line.length() - 1);
                String[] data = line.split("\\|");
                boolean isActive;
                if(data[6].trim().equals("true"))isActive=true;
                else isActive=false;
                Account account = new Account(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim(),isActive);
                accounts.add(account);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return accounts;
    }

    public static List<Book> readBook(String fileName) {
    	
        List<Book> books = new ArrayList<>();
        
        String path=f_path(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                Boolean isBorrow;
                if(data[7].trim().equals("true"))isBorrow=true;
                else isBorrow=false;
                Book book = new Book(data[0].trim(), data[1].trim(), data[2].trim(), LocalDate.parse(data[3].trim()), data[4].trim(), Integer.parseInt(data[5].trim()), Double.parseDouble(data[6].trim()),isBorrow);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return books;
    }

    public static List<BorrowSlip> readBorrowSlip(String fileName) {
        List<BorrowSlip> borrowSlips = new ArrayList<>();
        String path=f_path(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.substring(1, line.length() - 1);
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String maPhieuMuon = parts[0].trim();
                    LocalDate ngayMuon = LocalDate.parse(parts[1].trim());
                    String maTaiKhoan = parts[3].trim();
                    String maSach = parts[4].trim();
                    String trangThai=parts[5].trim();
                    borrowSlips.add(new BorrowSlip(maPhieuMuon, ngayMuon ,maTaiKhoan, maSach, trangThai));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return borrowSlips;
    }

    
    public static List<Category> readCategory(String fileName) {
        List<Category> categories = new ArrayList<>();
        String path=f_path(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                Category category = new Category(data[0].trim(), data[1].trim());
                categories.add(category);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return categories;
    }

    public static List<Violation> readViolation(String fileName) {
        List<Violation> violations = new ArrayList<>();
        String path = f_path(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                	String[] data = line.split("\\|");
                    String maViPham = data[0].trim();
                    String maPhieuMuon = data[1].trim();
                    String maTaiKhoan = data[2].trim();
                    String lyDo = data[3].trim();
                    int soNgayViPham = Integer.parseInt(data[4].trim());
                    double soTienPhat = Double.parseDouble(data[5].trim());
                    Violation violation = new Violation(maViPham, maPhieuMuon, maTaiKhoan, lyDo, soNgayViPham, soTienPhat);
                    violations.add(violation);

            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return violations;
    }
    
    public static List<Librarian> readLibrarian(String fileName) {
        List<Librarian> librarians = new ArrayList<>();
        String path=f_path(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                Librarian librarian = new Librarian(data[0].trim(),data[1].trim(), data[2].trim());
                librarians.add(librarian);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return librarians;
    }
}
