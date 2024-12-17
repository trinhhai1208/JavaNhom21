package frontend.components.user;

import backend.models.Category;
import backend.models.Violation;
import backend.utils.SessionManager;
import frontend.MainF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static backend.utils.ReadData.readCategory;
import static backend.utils.ReadData.readViolation;

public class Dashboard extends JFrame {
    public Dashboard() {
        SwingUtilities.invokeLater(() -> {
            // Lấy tên người dùng từ SessionManager
            String username = SessionManager.getCurrentUser().getTenDangNhap();

            // Tạo nút
            JButton btnSearchBook = new JButton("Tìm kiếm sách");
            JButton btnRenewBook = new JButton("Xem chi tiết phiếu mượn");
            JButton btnViewPersonalInfo = new JButton("Xem thông tin cá nhân");
            JButton btnLogout = new JButton("Đăng xuất");

            // Tạo nhãn chào mừng
            JLabel greetingLabel = new JLabel("Xin chào, " + username + "!");
            greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Set font and size

            // Set button actions
            btnSearchBook.addActionListener(e -> handleSearchBook());
            btnRenewBook.addActionListener(e -> handleRenewBook());
            btnViewPersonalInfo.addActionListener(e -> handleViewPersonalInfo());
            btnLogout.addActionListener(e -> handleLogout());

            //Tạo bảng để giữ các nút căn trái
            JPanel leftButtonPanel = new JPanel();
            leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Horizontal layout with gaps
            leftButtonPanel.add(btnSearchBook);
            leftButtonPanel.add(btnRenewBook);
            leftButtonPanel.add(btnViewPersonalInfo);

            //Tạo bảng giữ nút đăng xuất căn phải
            JPanel rightButtonPanel = new JPanel();
            rightButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Horizontal layout with gaps
            rightButtonPanel.add(greetingLabel); // Add the greeting label
            rightButtonPanel.add(btnLogout);

            //Tạo một bảng để giữ cả hai bảng nút
            JPanel mainButtonPanel = new JPanel();
            mainButtonPanel.setLayout(new BorderLayout());
            mainButtonPanel.add(leftButtonPanel, BorderLayout.WEST); // Left-aligned buttons
            mainButtonPanel.add(rightButtonPanel, BorderLayout.EAST); // Right-aligned logout button

            // Set a preferred height for the button panel (relative)
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int panelHeight = screenSize.height / 3;
            mainButtonPanel.setPreferredSize(new Dimension(screenSize.width, panelHeight));

            // Create the content panel with BorderLayout
            JPanel contentPanel = new JPanel(new BorderLayout());

            // Tạo tiêu đề cho Category
            JLabel categoryHeader = new JLabel("Danh mục");
            categoryHeader.setFont(new Font("Arial", Font.PLAIN, 24));
            categoryHeader.setSize(100, 50);
            categoryHeader.setForeground(Color.GRAY);
            categoryHeader.setHorizontalAlignment(SwingConstants.CENTER);

            // Tạo bảng bảng danh mục
            JPanel categoryPanel = new JPanel(new BorderLayout());
            JTable categoryTable = createCategoryTable(); // Create category table
            JScrollPane categoryScrollPane = new JScrollPane(categoryTable); // Add scroll pane to category table
            categoryPanel.add(categoryHeader, BorderLayout.NORTH);
            categoryPanel.add(categoryScrollPane, BorderLayout.CENTER); // Add category title to category panel

            // Tạo tiêu đề cho Violation
            JLabel violationHeader = new JLabel("Vi phạm");
            violationHeader.setFont(new Font("Arial", Font.PLAIN, 24));
            violationHeader.setSize(100, 50);
            violationHeader.setForeground(Color.GRAY);
            violationHeader.setHorizontalAlignment(SwingConstants.CENTER);

            // Tạo bảng violation
            JPanel violationPanel = new JPanel(new BorderLayout());
            JTable violationTable = createViolationTable(); // Create violation table
            JScrollPane violationScrollPane = new JScrollPane(violationTable); // Add scroll pane to violation table
            violationPanel.add(violationHeader, BorderLayout.NORTH); // Add violation table to violation panel
            violationPanel.add(violationScrollPane, BorderLayout.CENTER); // Add violation title to violation panel

            // Add category panel to the WEST (left) of the content panel
            contentPanel.add(categoryPanel, BorderLayout.WEST);

            // Add violation panel to the CENTER of the content panel
            contentPanel.add(violationPanel, BorderLayout.CENTER);

            // Add content panel to the main frame
            add(contentPanel, BorderLayout.CENTER);

            // Create the main frame and add the panel to it
            this.setTitle("Library Menu");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(900, 600);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setLocationRelativeTo(null);
            this.setLayout(new BorderLayout());
            this.add(mainButtonPanel, BorderLayout.NORTH); // Add the main button panel
            this.add(contentPanel, BorderLayout.CENTER); // Add the category and violation panel
            this.setVisible(true);
        });
    }

    // Placeholder methods for button actions
    private void handleSearchBook() {
        SearchBook.showSearchBookLayout(this);
    }

    private void handleRenewBook() {
        Dashboard.this.dispose();
        BorrowBook borrowBookWindow = new BorrowBook(Dashboard.this);
        borrowBookWindow.loadBorrowedBooks();
    }

    private void handleViewHistory() {
    }

    private void handleViewPersonalInfo() {
        new PersonalInfoView(this);
        setVisible(false);
        dispose();
    }

    private void handleLogout() {
        // Invalidate the session
        SessionManager.logout();

        // Đóng dashboard and hiển thị login window
        MainF firstPage = new MainF();
        firstPage.getFrame().setVisible(true);
        setVisible(false);
        dispose();
    }

    // Tạo bảng danh sách
    private JTable createCategoryTable() {
        java.util.List<Category> categories = readCategory("../DemoDB/Category.txt");
        String[] columnNames = {"ID", "Title"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Category category : categories) {
            Object[] rowData = {category.getMaDanhMuc(), category.getTenDanhMuc()};
            tableModel.addRow(rowData);
        }

        return new JTable(tableModel);
    }

    // tạo bảng Vi phạm
    private JTable createViolationTable() {
        java.util.List<Violation> violations = readViolation("../DemoDB/Violation.txt");
        String[] columnNames = {"Mã Phiếu Mượn", "Lí Do", "Số Ngày Vi Phạm", "Số Tiền Phạt"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);



        for (Violation v : violations) {
            if (SessionManager.getCurrentUser().getMaTaiKhoan().equals(v.getMaTaiKhoan())) {
                Object[] rowData = {v.getMaPhieuMuon(), v.getLyDo(), v.getSoNgayViPham(), v.getSoTienPhat()};
                tableModel.addRow(rowData);
            }
        }
        return new JTable(tableModel);
    }
}
