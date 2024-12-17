package frontend.components.user;

import backend.controllers.UserController;
import backend.models.Account;
import backend.models.Book;
import backend.models.BorrowSlip;
import backend.utils.ReadData;
import backend.utils.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowBook extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable borrowedBooksTable;
    private JTable waitingBooksTable;
    private DefaultTableModel borrowedTableModel;
    private DefaultTableModel waitingTableModel;
    private JFrame parentFrame;

    public BorrowBook(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setTitle("Xem lịch sử mượn sách");
        setSize(900, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Make the frame fullscreen
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Create the table models and tables
        String[] borrowedColumnNames = {"STT", "Tên sách", "Ngày mượn", "Ngày trả dự kiến"};
        borrowedTableModel = new DefaultTableModel(borrowedColumnNames, 0);
        borrowedBooksTable = new JTable(borrowedTableModel);
        JScrollPane borrowedScrollPane = new JScrollPane(borrowedBooksTable);

        String[] waitingColumnNames = {"STT", "Tên sách", "Ngày mượn"};
        waitingTableModel = new DefaultTableModel(waitingColumnNames, 0);
        waitingBooksTable = new JTable(waitingTableModel);
        JScrollPane waitingScrollPane = new JScrollPane(waitingBooksTable);

        // Tạo tiêu đề cho mỗi bảng
        JLabel borrowedHeader = new JLabel("Sách đã mượn");
        borrowedHeader.setFont(new Font("Arial", Font.PLAIN, 24));
        borrowedHeader.setSize(100, 50);
        borrowedHeader.setForeground(Color.GRAY);
        borrowedHeader.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel waitingHeader = new JLabel("Sách chờ phê duyệt");
        waitingHeader.setFont(new Font("Arial", Font.PLAIN, 24));
        waitingHeader.setSize(100, 50);
        waitingHeader.setForeground(Color.GRAY);
        waitingHeader.setHorizontalAlignment(SwingConstants.CENTER);

        // Căn tiêu đề cho mỗi bảng
        JPanel borrowedPanel = new JPanel(new BorderLayout());
        borrowedPanel.add(borrowedHeader, BorderLayout.NORTH);
        borrowedPanel.add(borrowedScrollPane, BorderLayout.CENTER);

        JPanel waitingPanel = new JPanel(new BorderLayout());
        waitingPanel.add(waitingHeader, BorderLayout.NORTH);
        waitingPanel.add(waitingScrollPane, BorderLayout.CENTER);

        // Tạo một ngăn chia để giữ hai bảng cạnh nhau
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, borrowedPanel, waitingPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);


        // Tạo nút "Quay lại "
        JButton btnBack = new JButton("Quay lại");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                parentFrame.setVisible(true);
            }
        });

        //Thêm nút "Quay lại" ở cuối cửa sổ
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Căn giữa cửa sổ và hiển thị


        // Tải dữ liệu cho cả hai bảng
        loadBorrowedBooks();
        loadWaitingBooks();
    }

    // Load sách mượn
    public void loadBorrowedBooks() {
        String userId = SessionManager.getCurrentUser().getMaTaiKhoan();
        List<BorrowSlip> borrowedBooks = UserController.getBorrowedBooks(userId);
        updateBorrowedTable(borrowedBooks);
    }

    // Cập nhật bảng
    private void updateBorrowedTable(List<BorrowSlip> borrowedBooks) {
        borrowedTableModel.setRowCount(0);
        for (int i = 0; i < borrowedBooks.size(); i++) {
            BorrowSlip borrowSlip = borrowedBooks.get(i);
            Book book = getBookByCode(borrowSlip.getMaSach());
            borrowedTableModel.addRow(new Object[]{i + 1, book.getTenSach(), borrowSlip.getNgayMuon(), borrowSlip.getNgayTra()});
        }
    }

    // load sách Đợi duyệt
    private void loadWaitingBooks() {
        List<WaitingBook> waitingBooks = getWaitingBooks();
        updateWaitingTable(waitingBooks);
    }

    private List<WaitingBook> getWaitingBooks() {
        Account a = SessionManager.getCurrentUser();
        List<WaitingBook> waitingBooks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ReadData.f_path("../DemoDB/borrow-slip.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    line = line.substring(1, line.length() - 1);
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6 && "Pending".equals(parts[5].trim()) && a.getMaTaiKhoan().equals(parts[3].trim())) {
//                    String maSach = parts[0].trim();
                        String ngayMuon = parts[1].trim();
                        String ngayTra = parts[2].trim();
                        String tenSach = getBookByCode(parts[4].trim()).getTenSach();
                        waitingBooks.add(new WaitingBook(tenSach, ngayMuon, ngayTra));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return waitingBooks;
    }

    private void updateWaitingTable(List<WaitingBook> waitingBooks) {
        waitingTableModel.setRowCount(0);
        for (int i = 0; i < waitingBooks.size(); i++) {
            WaitingBook waitingBook = waitingBooks.get(i);
            waitingTableModel.addRow(new Object[]{i + 1, waitingBook.getBookTitle(), waitingBook.getBorrowDate()});
        }
    }

    private static Book getBookByCode(String bookCode) {
        try (BufferedReader br = new BufferedReader(new FileReader(ReadData.f_path("../DemoDB/Book.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                //line = line.substring(1, line.length() - 1);
                String[] parts = line.split("\\|");
                if (parts.length >= 8 && parts[0].trim().equals(bookCode)) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    String author = parts[2].trim();
                    String releaseDate = parts[3].trim();
                    String category = parts[4].trim();
                    int quantity = Integer.parseInt(parts[5].trim());
                    double price = Double.parseDouble(parts[6].trim());
                    return new Book(code, name, author, LocalDate.parse(releaseDate), category, quantity, price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    private class WaitingBook {
        private String bookTitle;
        private String borrowDate;
        private String expectedReturnDate;

        public WaitingBook(String bookTitle, String borrowDate, String expectedReturnDate) {
            this.bookTitle = bookTitle;
            this.borrowDate = borrowDate;
            this.expectedReturnDate = expectedReturnDate;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getBorrowDate() {
            return borrowDate;
        }

        public String getExpectedReturnDate() {
            return expectedReturnDate;
        }
    }
}
