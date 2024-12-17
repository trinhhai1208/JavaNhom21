package backend.utils;

import backend.models.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteData {
	//ghi dữ liệu
    public static void writeAccount(List<Account> accounts, String fileName) {
    	String path=ReadData.f_path(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Account account : accounts) {
                bw.write(account.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void writeBook(List<Book> books, String fileName) {
    	String path=ReadData.f_path(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Book book : books) {
                bw.write(book.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void writeBorrowSlip(List<BorrowSlip> slips, String fileName) {
    	String path=ReadData.f_path(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (BorrowSlip slip : slips) {
                bw.write(slip.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }



    public static void writeCategory(List<Category> categories, String fileName) {
    	String path=ReadData.f_path(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Category category : categories) {
                bw.write(category.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static void writeViolation(List<Violation> violations, String fileName) {
    	String path=ReadData.f_path(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Violation violation : violations) {
                bw.write(violation.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    public static void writeLibrarian(List<Librarian> librarians,String fileName) {
    	String path=ReadData.f_path(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Librarian l : librarians) {
                bw.write(l.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}

