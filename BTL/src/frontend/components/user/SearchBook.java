package frontend.components.user;

import backend.controllers.UserController;
import backend.models.Account;
import backend.models.Book;
import backend.models.BorrowSlip;
import backend.utils.ReadData;
import backend.utils.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchBook {
    public static void showSearchBookLayout(JFrame parentFrame) {
        // Tạo một JFrame cho layout tìm kiếm sách
        final JFrame searchFrame = new JFrame("Tìm kiếm sách");
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        searchFrame.setResizable(true);
        searchFrame.getContentPane().setLayout(new BorderLayout());

        // JFrame ở center
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
        parentFrame.setVisible(false);

        // Tạo các thành phần UI cho layout tìm kiếm sách
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel lblSearch = new JLabel("Tìm kiếm ");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 16)); // Set larger font for label
        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(300, 30));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnBack = new JButton("Quay lại");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnBack);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Bảng xuất ra thông tin 1 phần của sách
        String[] columnNames = {"STT", "Mã sách", "Tên sách", "Thể loại", "Thao tác"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        List<Book> allBooks = UserController.searchBooks("");
        if (allBooks.isEmpty()) {
            tableModel.setRowCount(0); // Clear existing rows
            tableModel.addRow(new Object[]{"", "", "No books found", "", ""});
        } else {
            updateTable(allBooks, tableModel);
        }
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the button column is editable
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 4) {
                    return new ButtonRenderer();
                }
                return super.getCellRenderer(row, column);
            }
        };

        // Thêm  "Thao tác" column
        table.getColumn("Thao tác").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1350, 800)); // Set preferred size for the scroll pane

        // Thêm các thành phần UI vào JFrame
        searchFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
        searchFrame.getContentPane().add(scrollPane, BorderLayout.WEST);

        // Method to adjust column widths
        adjustColumnWidths(table, new double[]{0.10, 0.10, 0.40, 0.30, 0.10});

        // Xử lý sự kiện khi nhấn vào nút "Tìm kiếm"
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = txtSearch.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(searchFrame, "Vui lòng nhập từ khóa tìm kiếm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<Book> books = UserController.searchBooks(keyword);
                    if (books.isEmpty()) {
                        tableModel.setRowCount(0); // Clear existing rows
                        tableModel.addRow(new Object[]{"", "", "No books found", "", ""});
                    } else {
                        updateTable(books, tableModel);
                    }
                }
                adjustColumnWidths(table, new double[]{0.10, 0.10, 0.40, 0.30, 0.10});
            }
        });

        // Xử lý sự kiện khi nhấn vào nút "Quay lại"
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFrame.dispose();
                parentFrame.setVisible(true);
            }
        });

        // Add a component listener to resize columns when the frame is resized
        searchFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustColumnWidths(table, new double[]{0.10, 0.10, 0.40, 0.30, 0.10});
            }
        });


    }

    // Điều chỉnh cột
    private static void adjustColumnWidths(JTable table, double[] percentages) {
        final int totalWidth = table.getWidth();
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < percentages.length; i++) {
            int pWidth = (int)(percentages[i] * totalWidth);
            columnModel.getColumn(i).setPreferredWidth(pWidth);
        }
    }

    // Cập nhật bảng khi nhập từ khóa tìm
    private static void updateTable(List<Book> books, DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Book book : books) {
            tableModel.addRow(new Object[]{books.indexOf(book) + 1, book.getMaSach(), book.getTenSach(), book.getTheLoai(), "Xem"});
        }
    }

    // Button renderer class
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Xem" : value.toString());
            return this;
        }
    }

    // Button editor class
    static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;
        private boolean isPushed;
        private final JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Xem" : value.toString();
            button.setText(label);
            this.row = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Lấy thông tin sách từ bảng
                String code = (String) table.getValueAt(row, 1);

                Book book = findBookByCode(code);
                if (book != null) {
                    showBookDetails(book);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        // Tìm sách theo từ khóa
        private Book findBookByCode(String code) {
            List<Book> books = UserController.searchBooks(""); // Load lại toàn bộ danh sách sách
            for (Book book : books) {
                if (book.getMaSach().equals(code)) {
                    return book;
                }
            }
            return null;
        }

        // chi tiết sách
        private void showBookDetails(Book book) {
            JFrame detailFrame = new JFrame("Chi tiết sách");
            detailFrame.setSize(400, 300);
            detailFrame.getContentPane().setLayout(new GridLayout(10, 1));

            JLabel lblCode = new JLabel("Mã sách: " + book.getMaSach());
            JLabel lblName = new JLabel("Tên sách: " + book.getTenSach());
            JLabel lblPublisher = new JLabel("NXB: " + book.getNXB());
            JLabel lblReleaseDate = new JLabel("Ngày phát hành: " + book.getNph());
            JLabel lblCategory = new JLabel("Thể loại: " + book.getTheLoai());
            JLabel lblQuantity = new JLabel("Số lượng: " + book.getSl());
            JLabel lblPrice = new JLabel("Giá: " + book.getGia());

            JButton btnBorrowBook = new JButton("Đặt mượn sách");
            JButton btnBack = new JButton("Quay lại");

            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    detailFrame.dispose();
                }
            });

            btnBorrowBook.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showDetailedBookInfo(book);
                }
            });

            detailFrame.getContentPane().add(lblCode);
            detailFrame.getContentPane().add(lblName);
            detailFrame.getContentPane().add(lblPublisher);
            detailFrame.getContentPane().add(lblReleaseDate);
            detailFrame.getContentPane().add(lblCategory);
            detailFrame.getContentPane().add(lblQuantity);
            detailFrame.getContentPane().add(lblPrice);
            detailFrame.getContentPane().add(btnBorrowBook);
            detailFrame.getContentPane().add(btnBack);

            detailFrame.setLocationRelativeTo(null);
            detailFrame.setVisible(true);
        }

        // Phiếu xác nhận sau khi chọn Đặt mượn sách
        private void showDetailedBookInfo(Book book) {
            JFrame detailedFrame = new JFrame("Thông tin chi tiết sách");
            detailedFrame.setSize(400, 300);
            detailedFrame.getContentPane().setLayout(new GridLayout(5, 1));

            JLabel lblCode = new JLabel("Mã sách: " + book.getMaSach());
            JLabel lblName = new JLabel("Tên sách: " + book.getTenSach());
            JLabel lblCategory = new JLabel("Thể loại: " + book.getTheLoai());
            JButton btnBack = new JButton("Quay lại");
            JButton btnConfirm = new JButton("Xác nhận");

            btnConfirm.setEnabled(book.getSl() > 0);

            btnBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    detailedFrame.dispose();
                }
            });

            btnConfirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //book.reduceQuantity(1);

                    // Viết phiếu mượn
                    writeBorrowSlip(book);

                    //Cập nhật tệp Book.txt để phản ánh số lượng mới
                    updateBookFile(book);

                    // Display a confirmation dialog
                    JOptionPane.showMessageDialog(detailedFrame, "Chờ xác nhận từ thủ thư", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    detailedFrame.dispose();
                }
            });

            detailedFrame.getContentPane().add(lblCode);
            detailedFrame.getContentPane().add(lblName);
            detailedFrame.getContentPane().add(lblCategory);
            detailedFrame.getContentPane().add(btnConfirm);
            detailedFrame.getContentPane().add(btnBack);

            detailedFrame.setLocationRelativeTo(null);
            detailedFrame.setVisible(true);
        }


        // Cập nhật file Book.txt
        private void updateBookFile(Book updatedBook) {
            String filePath = ReadData.f_path("../DemoDB/Book.txt");
            List<String> fileContent = new ArrayList<>();

            // Đọc toàn bộ nội dung của tệp vào danh sách
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
                return;
            }

            // Tìm và cập nhật thông tin sách
            for (int i = 0; i < fileContent.size(); i++) {
                String tempLine = fileContent.get(i);
                String line = tempLine.substring(1, tempLine.length() - 1);
                if (line.contains(updatedBook.getMaSach())) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 8) {
                        int quantity = Integer.parseInt(parts[5].trim());
                        parts[5] = " " + (quantity - 1) + " "; // Cập nhật số lượng
                        StringBuilder updatedLine = new StringBuilder("|");
                        for (String part : parts) {
                            updatedLine.append(part).append("|");
                        }
                        fileContent.set(i, updatedLine.toString());
                    }
                }
            }

            // Ghi lại nội dung đã cập nhật vào tệp
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : fileContent) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }

        public static void writeBorrowSlip(Book book) {
            String maPhieuMuon = generateBorrowSlipId();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ReadData.f_path("../DemoDB/borrow-slip.txt"), true))) {
                LocalDate ngayMuon = LocalDate.now();

                // Use SessionManager to get the current user
                Account currentUser = SessionManager.getCurrentUser();
                String maTaiKhoan = currentUser != null ? currentUser.getMaTaiKhoan() : "N/A";

                BorrowSlip borrowSlip = new BorrowSlip(maPhieuMuon, ngayMuon, maTaiKhoan, book.getMaSach(), "Pending"); // Assume newly created slip is pending
                // Write the borrow slip to file
                writer.write(borrowSlip.toString());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }

        // Viết chi tiết phiếu mượn
        private void writeBorrowSlipDetail(Book book, String maPhieuMuon) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("borrow-slip-detail.txt", true))) {
                String ngayTraDuKien = calculateDueDate(); // Implement this method to calculate the due date
                String ngayTraThucTe = "N/A"; // This will be empty initially as the book hasn't been returned yet
                int soLuong = 1;

                String borrowSlipDetail = String.format("| %-10s | %-30s | %-20s | %-10s | %-12s |",
                        book.getMaSach(), maPhieuMuon, ngayTraDuKien, ngayTraThucTe, soLuong);
                writer.write(borrowSlipDetail);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }

        // Sample method to generate a borrow slip ID
        private static String generateBorrowSlipId() {
            // Implement ID generation logic
            return "BS" + System.currentTimeMillis();
        }

        // Sample method to calculate the due date
        private String calculateDueDate() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 14); // Assuming a 14-day borrow period
            return new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
        }
    }
}

