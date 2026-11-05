package ui;

import data.HotelData;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {

    private final User currentUser;

    private JPanel contentPanel;
    private JPanel menuPanel;

    private final Color DARK = new Color(24, 32, 48);
    private final Color PANEL = new Color(36, 48, 68);
    private final Color BLUE = new Color(45, 130, 220);
    private final Color TEXT = new Color(235, 240, 250);

    private final Font FONT = new Font("Arial", Font.PLAIN, 14);
    private final Font BOLD = new Font("Arial", Font.BOLD, 15);

    public MainFrame(User user) {
        this.currentUser = user;

        setTitle("Hệ thống quản lý đặt phòng khách sạn");
        setSize(1250, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createHeader();
        createMenu();

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK);

        add(contentPanel, BorderLayout.CENTER);

        showDashboard();
    }

    // ================= HEADER =================

    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL);
        header.setPreferredSize(new Dimension(0, 65));
        header.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel title = new JLabel("HOTEL MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 21));

        JLabel user = new JLabel(
                "Xin chào: " + currentUser.getHoTen()
                        + "  |  " + currentUser.getVaiTro()
        );

        user.setForeground(Color.WHITE);
        user.setFont(FONT);

        header.add(title, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    // ================= MENU =================

    private void createMenu() {
        menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(220, 0));
        menuPanel.setBackground(DARK);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(menuPanel, BorderLayout.WEST);

        addMenuButton("Tổng quan", this::showDashboard);

        if (currentUser instanceof Admin) {
            addMenuButton("Quản lý phòng", this::showRooms);
            addMenuButton("Quản lý khách hàng", this::showCustomers);
            addMenuButton("Quản lý đặt phòng", this::showBookings);
            addMenuButton("Quản lý thanh toán", this::showPayments);
            addMenuButton("Quản lý tài khoản", this::showAccounts);
            addMenuButton("Báo cáo doanh thu", this::showReports);
        }

        if (currentUser instanceof Employee) {
            addMenuButton("Danh sách đặt phòng", this::showBookings);
            addMenuButton("Nhận phòng / Trả phòng", this::showReception);
            addMenuButton("Danh sách khách hàng", this::showCustomers);
            addMenuButton("Thanh toán", this::showPayments);
        }

        if (currentUser instanceof Customer) {
            addMenuButton("Tìm kiếm phòng", this::showRooms);
            addMenuButton("Đặt phòng", this::showBookings);
            addMenuButton("Lịch sử đặt phòng", this::showBookings);
            addMenuButton("Thanh toán", this::showPayments);
        }

        menuPanel.add(Box.createVerticalGlue());

        addMenuButton("Đăng xuất", () -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void addMenuButton(String text, Runnable action) {
        JButton button = new JButton(text);

        button.setMaximumSize(new Dimension(200, 45));
        button.setPreferredSize(new Dimension(200, 45));
        button.setBackground(PANEL);
        button.setForeground(Color.WHITE);
        button.setFont(BOLD);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(e -> action.run());

        menuPanel.add(button);
        menuPanel.add(Box.createVerticalStrut(8));
    }

    // ================= CONTENT =================

    private void setContent(JComponent component) {
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createPage(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBackground(DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel(title);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(heading, BorderLayout.NORTH);

        return panel;
    }

    private JLabel createCardLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 19));
        label.setOpaque(true);
        label.setBackground(PANEL);
        label.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        return label;
    }

    // ================= DASHBOARD =================

    private void showDashboard() {
        JPanel page = createPage("TỔNG QUAN HỆ THỐNG");

        JPanel cards = new JPanel(new GridLayout(2, 2, 15, 15));
        cards.setBackground(DARK);

        double revenue = 0;

        for (Payment p : HotelData.payments) {
            if ("Đã thanh toán".equals(p.getTrangThai())) {
                revenue += p.getSoTien();
            }
        }

        cards.add(createCardLabel(
                "TỔNG SỐ PHÒNG\n\n" + HotelData.rooms.size()
        ));

        cards.add(createCardLabel(
                "KHÁCH HÀNG\n\n" + HotelData.customers.size()
        ));

        cards.add(createCardLabel(
                "ĐẶT PHÒNG\n\n" + HotelData.bookings.size()
        ));

        cards.add(createCardLabel(
                "DOANH THU\n\n" + formatMoney(revenue)
        ));

        page.add(cards, BorderLayout.CENTER);

        JLabel welcome = new JLabel(
                "Chào mừng bạn đến với hệ thống quản lý đặt phòng khách sạn!"
        );

        welcome.setForeground(Color.LIGHT_GRAY);
        welcome.setFont(new Font("Arial", Font.PLAIN, 16));

        page.add(welcome, BorderLayout.SOUTH);

        setContent(page);
    }

    // ================= ROOM MANAGEMENT =================

    private void showRooms() {
        JPanel page = createPage("QUẢN LÝ PHÒNG");

        String[] columns = {
                "Mã phòng", "Số phòng", "Tầng",
                "Loại phòng", "Giá phòng", "Trạng thái"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = createTable(model);

        refreshRoomTable(model);

        JButton add = createButton("Thêm phòng");
        JButton edit = createButton("Sửa phòng");
        JButton delete = createButton("Xóa phòng");

        JPanel buttons = new JPanel();
        buttons.setBackground(DARK);

        buttons.add(add);
        buttons.add(edit);
        buttons.add(delete);

        add.addActionListener(e -> {
            JTextField number = new JTextField();
            JTextField floor = new JTextField();

            JComboBox<RoomType> type = new JComboBox<>(
                    HotelData.roomTypes.toArray(new RoomType[0])
            );

            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

            form.add(new JLabel("Số phòng:"));
            form.add(number);

            form.add(new JLabel("Tầng:"));
            form.add(floor);

            form.add(new JLabel("Loại phòng:"));
            form.add(type);

            if (JOptionPane.showConfirmDialog(
                    this, form, "Thêm phòng",
                    JOptionPane.OK_CANCEL_OPTION
            ) != JOptionPane.OK_OPTION) return;

            try {
                String soPhong = number.getText().trim();
                int tang = Integer.parseInt(floor.getText().trim());

                if (soPhong.isEmpty() || tang < 1 || type.getSelectedItem() == null) {
                    throw new IllegalArgumentException();
                }

                for (Room r : HotelData.rooms) {
                    if (r.getSoPhong().equalsIgnoreCase(soPhong)) {
                        JOptionPane.showMessageDialog(this, "Số phòng đã tồn tại!");
                        return;
                    }
                }

                RoomType selected = (RoomType) type.getSelectedItem();

                HotelData.rooms.add(new Room(
                        HotelData.nextRoomId(),
                        soPhong,
                        tang,
                        "Trống",
                        selected.getMoTa(),
                        selected
                ));

                refreshRoomTable(model);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin hợp lệ!");
            }
        });

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn phòng cần sửa!");
                return;
            }

            Room room = HotelData.rooms.get(table.convertRowIndexToModel(row));

            JTextField number = new JTextField(room.getSoPhong());
            JTextField floor = new JTextField(String.valueOf(room.getTang()));

            String[] statuses = {"Trống", "Đã đặt", "Đang sử dụng", "Bảo trì"};
            JComboBox<String> status = new JComboBox<>(statuses);
            status.setSelectedItem(room.getTrangThai());

            JComboBox<RoomType> type = new JComboBox<>(
                    HotelData.roomTypes.toArray(new RoomType[0])
            );
            type.setSelectedItem(room.getLoaiPhong());

            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

            form.add(new JLabel("Số phòng:"));
            form.add(number);
            form.add(new JLabel("Tầng:"));
            form.add(floor);
            form.add(new JLabel("Loại phòng:"));
            form.add(type);
            form.add(new JLabel("Trạng thái:"));
            form.add(status);

            if (JOptionPane.showConfirmDialog(
                    this, form, "Sửa phòng",
                    JOptionPane.OK_CANCEL_OPTION
            ) == JOptionPane.OK_OPTION) {
                try {
                    if (number.getText().trim().isEmpty()) throw new Exception();

                    room.setSoPhong(number.getText().trim());
                    room.setTang(Integer.parseInt(floor.getText().trim()));
                    room.setLoaiPhong((RoomType) type.getSelectedItem());
                    room.setTrangThai((String) status.getSelectedItem());

                    refreshRoomTable(model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Thông tin không hợp lệ!");
                }
            }
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn phòng cần xóa!");
                return;
            }

            Room room = HotelData.rooms.get(table.convertRowIndexToModel(row));

            boolean used = HotelData.bookings.stream().anyMatch(
                    b -> b.getPhong() == room
                            && !b.getTrangThai().equals("Đã hủy")
                            && !b.getTrangThai().equals("Đã trả phòng")
            );

            if (used) {
                JOptionPane.showMessageDialog(
                        this, "Không thể xóa phòng đang có đặt phòng!"
                );
                return;
            }

            if (JOptionPane.showConfirmDialog(
                    this, "Bạn có chắc muốn xóa phòng này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                HotelData.rooms.remove(room);
                refreshRoomTable(model);
            }
        });

        page.add(new JScrollPane(table), BorderLayout.CENTER);
        page.add(buttons, BorderLayout.SOUTH);

        setContent(page);
    }

    private void refreshRoomTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Room r : HotelData.rooms) {
            model.addRow(new Object[]{
                    r.getMaPhong(),
                    r.getSoPhong(),
                    r.getTang(),
                    r.getLoaiPhong().getTenLoaiPhong(),
                    formatMoney(r.getLoaiPhong().getGiaPhong()),
                    r.getTrangThai()
            });
        }
    }

    // ================= CUSTOMER MANAGEMENT =================

    private void showCustomers() {
        JPanel page = createPage("QUẢN LÝ KHÁCH HÀNG");

        String[] columns = {
                "Mã khách hàng", "Họ tên", "Email",
                "Số điện thoại", "Địa chỉ", "Ngày sinh"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = createTable(model);

        refreshCustomerTable(model);

        JTextField search = new JTextField(15);

        JButton add = createButton("Thêm khách hàng");
        JButton edit = createButton("Sửa thông tin");
        JButton delete = createButton("Xóa khách hàng");
        JButton find = createButton("Tìm kiếm");
        JButton reset = createButton("Làm mới");

        JPanel top = new JPanel();
        top.setBackground(DARK);

        top.add(new JLabel("Tìm khách hàng:"));
        top.add(search);
        top.add(find);
        top.add(reset);

        JPanel buttons = new JPanel();
        buttons.setBackground(DARK);

        buttons.add(add);
        buttons.add(edit);
        buttons.add(delete);

        add.addActionListener(e -> {
            JTextField name = new JTextField();
            JTextField email = new JTextField();
            JTextField phone = new JTextField();
            JTextField address = new JTextField();
            JTextField birthday = new JTextField("2000-01-01");

            JTextField password = new JTextField("123");

            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

            form.add(new JLabel("Họ tên:"));
            form.add(name);
            form.add(new JLabel("Email:"));
            form.add(email);
            form.add(new JLabel("Mật khẩu:"));
            form.add(password);
            form.add(new JLabel("Số điện thoại:"));
            form.add(phone);
            form.add(new JLabel("Địa chỉ:"));
            form.add(address);
            form.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
            form.add(birthday);

            if (JOptionPane.showConfirmDialog(
                    this, form, "Thêm khách hàng",
                    JOptionPane.OK_CANCEL_OPTION
            ) != JOptionPane.OK_OPTION) return;

            try {
                String n = name.getText().trim();
                String em = email.getText().trim();
                String pw = password.getText().trim();

                if (n.isEmpty() || em.isEmpty() || pw.isEmpty()) {
                    throw new Exception();
                }

                if (HotelData.customers.stream().anyMatch(
                        c -> c.getEmail().equalsIgnoreCase(em)
                )) {
                    JOptionPane.showMessageDialog(this, "Email đã tồn tại!");
                    return;
                }

                Customer c = new Customer(
                        HotelData.nextCustomerId(),
                        n, em, pw,
                        phone.getText().trim(),
                        address.getText().trim(),
                        LocalDate.parse(birthday.getText().trim())
                );

                HotelData.customers.add(c);
                refreshCustomerTable(model);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Vui lòng nhập đúng thông tin và ngày sinh theo yyyy-MM-dd!"
                );
            }
        });

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn khách hàng!");
                return;
            }

            Customer c = HotelData.customers.get(table.convertRowIndexToModel(row));

            JTextField name = new JTextField(c.getHoTen());
            JTextField email = new JTextField(c.getEmail());
            JTextField phone = new JTextField(c.getSoDienThoai());
            JTextField address = new JTextField(c.getDiaChi());
            JTextField birthday = new JTextField(c.getNgaySinh().toString());

            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

            form.add(new JLabel("Họ tên:"));
            form.add(name);
            form.add(new JLabel("Email:"));
            form.add(email);
            form.add(new JLabel("Số điện thoại:"));
            form.add(phone);
            form.add(new JLabel("Địa chỉ:"));
            form.add(address);
            form.add(new JLabel("Ngày sinh:"));
            form.add(birthday);

            if (JOptionPane.showConfirmDialog(
                    this, form, "Cập nhật khách hàng",
                    JOptionPane.OK_CANCEL_OPTION
            ) == JOptionPane.OK_OPTION) {
                try {
                    String em = email.getText().trim();

                    boolean duplicate = HotelData.customers.stream().anyMatch(
                            other -> other != c
                                    && other.getEmail().equalsIgnoreCase(em)
                    );

                    if (duplicate || em.isEmpty() || name.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Tên hoặc email không hợp lệ!");
                        return;
                    }

                    c.setHoTen(name.getText().trim());
                    c.setEmail(em);
                    c.setSoDienThoai(phone.getText().trim());
                    c.setDiaChi(address.getText().trim());
                    c.setNgaySinh(LocalDate.parse(birthday.getText().trim()));

                    refreshCustomerTable(model);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!");
                }
            }
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn khách hàng!");
                return;
            }

            Customer c = HotelData.customers.get(table.convertRowIndexToModel(row));

            boolean hasBooking = HotelData.bookings.stream().anyMatch(
                    b -> b.getKhachHang() == c
                            && !b.getTrangThai().equals("Đã hủy")
            );

            if (hasBooking) {
                JOptionPane.showMessageDialog(
                        this, "Không thể xóa khách hàng có lịch sử đặt phòng!"
                );
                return;
            }

            if (JOptionPane.showConfirmDialog(
                    this, "Xóa khách hàng " + c.getHoTen() + "?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {
                HotelData.customers.remove(c);
                refreshCustomerTable(model);
            }
        });

        find.addActionListener(e -> {
            String key = search.getText().trim().toLowerCase();

            model.setRowCount(0);

            for (Customer c : HotelData.customers) {
                if (c.getHoTen().toLowerCase().contains(key)
                        || c.getMaKhachHang().toLowerCase().contains(key)
                        || c.getEmail().toLowerCase().contains(key)
                        || c.getSoDienThoai().contains(key)) {
                    addCustomerRow(model, c);
                }
            }
        });

        reset.addActionListener(e -> {
            search.setText("");
            refreshCustomerTable(model);
        });

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.setBackground(DARK);
        center.add(top, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        page.add(center, BorderLayout.CENTER);
        page.add(buttons, BorderLayout.SOUTH);

        setContent(page);
    }

    private void refreshCustomerTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Customer c : HotelData.customers) {
            addCustomerRow(model, c);
        }
    }

    private void addCustomerRow(DefaultTableModel model, Customer c) {
        model.addRow(new Object[]{
                c.getMaKhachHang(),
                c.getHoTen(),
                c.getEmail(),
                c.getSoDienThoai(),
                c.getDiaChi(),
                c.getNgaySinh()
        });
    }

    // ================= BOOKING MANAGEMENT =================

    private void showBookings() {
        JPanel page = createPage("QUẢN LÝ ĐẶT PHÒNG");

        String[] columns = {
                "Mã đặt phòng", "Khách hàng", "Phòng",
                "Ngày nhận", "Ngày trả", "Số khách",
                "Tổng tiền", "Trạng thái"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = createTable(model);

        refreshBookingTable(model);

        JButton add = createButton("Đặt phòng");
        JButton cancel = createButton("Hủy đặt phòng");

        JPanel buttons = new JPanel();
        buttons.setBackground(DARK);
        buttons.add(add);
        buttons.add(cancel);

        add.addActionListener(e -> {
            if (HotelData.customers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa có khách hàng!");
                return;
            }

            List<Room> available = HotelData.rooms.stream()
                    .filter(r -> r.getTrangThai().equals("Trống"))
                    .toList();

            if (available.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không còn phòng trống!");
                return;
            }

            JComboBox<Customer> customer = new JComboBox<>(
                    HotelData.customers.toArray(new Customer[0])
            );

            JComboBox<Room> room = new JComboBox<>(
                    available.toArray(new Room[0])
            );

            JTextField checkIn = new JTextField(LocalDate.now().toString());
            JTextField checkOut = new JTextField(LocalDate.now().plusDays(1).toString());
            JTextField guests = new JTextField("2");

            JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

            form.add(new JLabel("Khách hàng:"));
            form.add(customer);
            form.add(new JLabel("Phòng:"));
            form.add(room);
            form.add(new JLabel("Ngày nhận (yyyy-MM-dd):"));
            form.add(checkIn);
            form.add(new JLabel("Ngày trả (yyyy-MM-dd):"));
            form.add(checkOut);
            form.add(new JLabel("Số lượng khách:"));
            form.add(guests);

            if (JOptionPane.showConfirmDialog(
                    this, form, "Tạo đặt phòng",
                    JOptionPane.OK_CANCEL_OPTION
            ) != JOptionPane.OK_OPTION) return;

            try {
                LocalDate in = LocalDate.parse(checkIn.getText().trim());
                LocalDate out = LocalDate.parse(checkOut.getText().trim());
                int count = Integer.parseInt(guests.getText().trim());

                if (!out.isAfter(in) || count < 1 || in.isBefore(LocalDate.now())) {
                    throw new Exception();
                }

                Customer c = (Customer) customer.getSelectedItem();
                Room r = (Room) room.getSelectedItem();

                boolean conflict = HotelData.bookings.stream().anyMatch(b ->
                        b.getPhong() == r
                                && !b.getTrangThai().equals("Đã hủy")
                                && !b.getTrangThai().equals("Đã trả phòng")
                                && in.isBefore(b.getNgayTra())
                                && out.isAfter(b.getNgayNhan())
                );

                if (conflict) {
                    JOptionPane.showMessageDialog(this, "Phòng đã được đặt trong khoảng thời gian này!");
                    return;
                }

                Booking b = new Booking(
                        HotelData.nextBookingId(),
                        c, r, LocalDate.now(),
                        in, out, count, "Đã xác nhận"
                );

                HotelData.bookings.add(b);

                r.setTrangThai(
                        in.equals(LocalDate.now()) ? "Đang sử dụng" : "Đã đặt"
                );

                HotelData.payments.add(new Payment(
                        HotelData.nextPaymentId(),
                        b, "Tiền mặt",
                        b.getTongTien(),
                        "Chưa thanh toán",
                        null
                ));

                refreshBookingTable(model);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Thông tin ngày hoặc số lượng khách không hợp lệ!"
                );
            }
        });

        cancel.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn đặt phòng!");
                return;
            }

            Booking b = HotelData.bookings.get(table.convertRowIndexToModel(row));

            if (b.getTrangThai().equals("Đã hủy")
                    || b.getTrangThai().equals("Đã trả phòng")) {
                JOptionPane.showMessageDialog(this, "Đặt phòng này không thể hủy!");
                return;
            }

            if (JOptionPane.showConfirmDialog(
                    this, "Bạn có muốn hủy đặt phòng này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION
            ) == JOptionPane.YES_OPTION) {

                b.setTrangThai("Đã hủy");

                boolean roomStillBooked = HotelData.bookings.stream().anyMatch(other ->
                        other != b
                                && other.getPhong() == b.getPhong()
                                && !other.getTrangThai().equals("Đã hủy")
                                && !other.getTrangThai().equals("Đã trả phòng")
                );

                if (!roomStillBooked) {
                    b.getPhong().setTrangThai("Trống");
                }

                refreshBookingTable(model);
            }
        });

        page.add(new JScrollPane(table), BorderLayout.CENTER);
        page.add(buttons, BorderLayout.SOUTH);

        setContent(page);
    }

    private void refreshBookingTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Booking b : HotelData.bookings) {
            model.addRow(new Object[]{
                    b.getMaDatPhong(),
                    b.getKhachHang().getHoTen(),
                    b.getPhong().getSoPhong(),
                    b.getNgayNhan(),
                    b.getNgayTra(),
                    b.getSoLuongKhach(),
                    formatMoney(b.getTongTien()),
                    b.getTrangThai()
            });
        }
    }

    // ================= PAYMENT =================

    private void showPayments() {
        JPanel page = createPage("QUẢN LÝ THANH TOÁN");

        String[] columns = {
                "Mã thanh toán", "Mã đặt phòng",
                "Khách hàng", "Số tiền",
                "Phương thức", "Trạng thái"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = createTable(model);

        refreshPaymentTable(model);

        JButton pay = createButton("Thanh toán");

        pay.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn hóa đơn!");
                return;
            }

            Payment p = HotelData.payments.get(table.convertRowIndexToModel(row));

            if (!p.getTrangThai().equals("Chưa thanh toán")) {
                JOptionPane.showMessageDialog(this, "Hóa đơn không ở trạng thái chờ thanh toán!");
                return;
            }

            String[] methods = {"Tiền mặt", "Chuyển khoản", "Thẻ ngân hàng"};

            String method = (String) JOptionPane.showInputDialog(
                    this, "Chọn phương thức thanh toán:",
                    "Thanh toán", JOptionPane.QUESTION_MESSAGE,
                    null, methods, methods[0]
            );

            if (method != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Xác nhận thanh toán " + formatMoney(p.getSoTien()) + "?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    p.thanhToan();

                    refreshPaymentTable(model);

                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                }
            }
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(DARK);
        bottom.add(pay);

        page.add(new JScrollPane(table), BorderLayout.CENTER);
        page.add(bottom, BorderLayout.SOUTH);

        setContent(page);
    }

    private void refreshPaymentTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Payment p : HotelData.payments) {
            model.addRow(new Object[]{
                    p.getMaThanhToan(),
                    p.getDatPhong().getMaDatPhong(),
                    p.getDatPhong().getKhachHang().getHoTen(),
                    formatMoney(p.getSoTien()),
                    p.getPhuongThuc(),
                    p.getTrangThai()
            });
        }
    }

    // ================= RECEPTION =================

    private void showReception() {
        JPanel page = createPage("NGHIỆP VỤ LỄ TÂN");

        String[] columns = {
                "Mã đặt phòng", "Khách hàng",
                "Phòng", "Ngày nhận", "Ngày trả", "Trạng thái"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = createTable(model);

        refreshReceptionTable(model);

        JButton checkIn = createButton("Làm thủ tục nhận phòng");
        JButton checkOut = createButton("Làm thủ tục trả phòng");

        checkIn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn đặt phòng!");
                return;
            }

            Booking b = getBookingAt(table, row);

            if (!b.getTrangThai().equals("Đã xác nhận")) {
                JOptionPane.showMessageDialog(this, "Không thể nhận phòng ở trạng thái này!");
                return;
            }

            if (b.getNgayNhan().isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Chưa đến ngày nhận phòng!");
                return;
            }

            b.setTrangThai("Đang sử dụng");
            b.getPhong().setTrangThai("Đang sử dụng");

            refreshReceptionTable(model);

            JOptionPane.showMessageDialog(this, "Đã nhận phòng thành công!");
        });

        checkOut.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn đặt phòng!");
                return;
            }

            Booking b = getBookingAt(table, row);

            if (!b.getTrangThai().equals("Đang sử dụng")) {
                JOptionPane.showMessageDialog(this, "Khách chưa nhận phòng!");
                return;
            }

            Payment payment = HotelData.payments.stream()
                    .filter(p -> p.getDatPhong() == b)
                    .findFirst().orElse(null);

            if (payment != null
                    && !payment.getTrangThai().equals("Đã thanh toán")) {
                JOptionPane.showMessageDialog(
                        this, "Vui lòng thanh toán trước khi trả phòng!"
                );
                return;
            }

            b.setTrangThai("Đã trả phòng");
            b.getPhong().setTrangThai("Trống");

            refreshReceptionTable(model);

            JOptionPane.showMessageDialog(this, "Trả phòng thành công!");
        });

        JPanel buttons = new JPanel();
        buttons.setBackground(DARK);
        buttons.add(checkIn);
        buttons.add(checkOut);

        page.add(new JScrollPane(table), BorderLayout.CENTER);
        page.add(buttons, BorderLayout.SOUTH);

        setContent(page);
    }

    private Booking getBookingAt(JTable table, int row) {
        return HotelData.bookings.get(table.convertRowIndexToModel(row));
    }

    private void refreshReceptionTable(DefaultTableModel model) {
        model.setRowCount(0);

        for (Booking b : HotelData.bookings) {
            model.addRow(new Object[]{
                    b.getMaDatPhong(),
                    b.getKhachHang().getHoTen(),
                    b.getPhong().getSoPhong(),
                    b.getNgayNhan(),
                    b.getNgayTra(),
                    b.getTrangThai()
            });
        }
    }

    // ================= ACCOUNT MANAGEMENT =================

    private void showAccounts() {
        JPanel page = createPage("QUẢN LÝ TÀI KHOẢN");

        String[] columns = {
                "Mã tài khoản", "Họ tên", "Email", "Vai trò", "Trạng thái"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (User u : HotelData.admins) {
            addAccountRow(model, u);
        }

        for (User u : HotelData.employees) {
            addAccountRow(model, u);
        }

        for (User u : HotelData.customers) {
            addAccountRow(model, u);
        }

        JTable table = createTable(model);

        JButton status = createButton("Khóa / Mở tài khoản");

        status.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Hãy chọn tài khoản!");
                return;
            }

            String id = model.getValueAt(
                    table.convertRowIndexToModel(row), 0
            ).toString();

            User selected = findUser(id);

            if (selected == null || selected instanceof Admin) {
                JOptionPane.showMessageDialog(
                        this, "Không thể thay đổi tài khoản quản trị viên!"
                );
                return;
            }

            selected.setTrangThai(!selected.isTrangThai());

            model.setValueAt(
                    selected.isTrangThai() ? "Hoạt động" : "Đã khóa",
                    table.convertRowIndexToModel(row),
                    4
            );
        });

        JPanel buttons = new JPanel();
        buttons.setBackground(DARK);
        buttons.add(status);

        page.add(new JScrollPane(table), BorderLayout.CENTER);
        page.add(buttons, BorderLayout.SOUTH);

        setContent(page);
    }

    private void addAccountRow(DefaultTableModel model, User u) {
        model.addRow(new Object[]{
                u.getMaNguoiDung(),
                u.getHoTen(),
                u.getEmail(),
                u.getVaiTro(),
                u.isTrangThai() ? "Hoạt động" : "Đã khóa"
        });
    }

    private User findUser(String id) {
        for (User u : HotelData.admins) {
            if (u.getMaNguoiDung().equals(id)) return u;
        }

        for (User u : HotelData.employees) {
            if (u.getMaNguoiDung().equals(id)) return u;
        }

        for (User u : HotelData.customers) {
            if (u.getMaNguoiDung().equals(id)) return u;
        }

        return null;
    }

    // ================= REPORT =================

    private void showReports() {
        JPanel page = createPage("BÁO CÁO DOANH THU");

        double total = 0;
        double paid = 0;
        double unpaid = 0;

        for (Payment p : HotelData.payments) {
            if (!p.getTrangThai().equals("Đã hủy")) {
                total += p.getSoTien();

                if (p.getTrangThai().equals("Đã thanh toán")) {
                    paid += p.getSoTien();
                } else {
                    unpaid += p.getSoTien();
                }
            }
        }

        JPanel report = new JPanel(new GridLayout(3, 1, 10, 10));
        report.setBackground(DARK);

        report.add(createCardLabel("TỔNG GIÁ TRỊ HÓA ĐƠN: " + formatMoney(total)));
        report.add(createCardLabel("DOANH THU ĐÃ THU: " + formatMoney(paid)));
        report.add(createCardLabel("CHƯA THANH TOÁN: " + formatMoney(unpaid)));

        page.add(report, BorderLayout.CENTER);

        setContent(page);
    }

    // ================= COMMON FUNCTIONS =================

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);

        table.setFont(FONT);
        table.setRowHeight(30);
        table.setForeground(TEXT);
        table.setBackground(PANEL);
        table.setSelectionBackground(BLUE);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(70, 85, 105));

        table.getTableHeader().setFont(BOLD);
        table.getTableHeader().setBackground(new Color(45, 65, 90));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        return table;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(BOLD);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    private String formatMoney(double money) {
        return String.format("%,.0f VNĐ", money);
    }
}