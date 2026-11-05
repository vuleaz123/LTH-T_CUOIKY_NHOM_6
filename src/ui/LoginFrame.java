
package ui;

import data.HotelData;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

    // ===== MÀU SẮC CHỦ ĐẠO =====
    private final Color NAVY = new Color(15, 27, 45);
    private final Color CARD = new Color(28, 43, 64);
    private final Color INPUT = new Color(19, 32, 51);
    private final Color GOLD = new Color(222, 185, 120);
    private final Color WHITE = new Color(248, 249, 252);
    private final Color GRAY = new Color(175, 188, 204);

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JCheckBox chkShowPassword;
    private JButton btnLogin;

    private char passwordChar;

    public LoginFrame() {

        setTitle("Đăng nhập - Grand Hotel");
        setSize(950, 720);
        setMinimumSize(new Dimension(850, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(createMainPanel());
    }

    // =========================================
    // BACKGROUND
    // =========================================

    private JPanel createMainPanel() {

        JPanel main = new JPanel(new GridBagLayout()) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                GradientPaint gradient = new GradientPaint(
                        0, 0, NAVY,
                        getWidth(), getHeight(),
                        new Color(32, 54, 78)
                );

                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Trang trí góc trên
                g2.setColor(new Color(255, 255, 255, 8));
                g2.fillOval(-170, -190, 400, 400);

                // Trang trí góc dưới
                g2.setColor(new Color(222, 185, 120, 13));
                g2.fillOval(
                        getWidth() - 210,
                        getHeight() - 210,
                        360, 360
                );

                g2.dispose();
            }
        };

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createHeader());
        content.add(Box.createVerticalStrut(32));
        content.add(createLoginCard());
        content.add(Box.createVerticalStrut(22));
        content.add(createFooter());

        main.add(content);

        return main;
    }

    // =========================================
    // HEADER
    // =========================================

    private JPanel createHeader() {

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel symbol = new JLabel("✦");
        symbol.setFont(new Font("Serif", Font.BOLD, 37));
        symbol.setForeground(GOLD);
        symbol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hotelName = new JLabel("GRAND HOTEL");
        hotelName.setFont(new Font("Serif", Font.BOLD, 35));
        hotelName.setForeground(WHITE);
        hotelName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel slogan = new JLabel(
                "HOTEL MANAGEMENT SYSTEM"
        );

        slogan.setFont(new Font("Arial", Font.PLAIN, 12));
        slogan.setForeground(GOLD);
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(symbol);
        header.add(Box.createVerticalStrut(5));
        header.add(hotelName);
        header.add(Box.createVerticalStrut(7));
        header.add(slogan);

        return header;
    }

    // =========================================
    // LOGIN CARD
    // =========================================

    private JPanel createLoginCard() {

        RoundedPanel card = new RoundedPanel(
                25, CARD, new Color(100, 117, 137)
        );

        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(440, 415));
        card.setMinimumSize(new Dimension(440, 415));
        card.setMaximumSize(new Dimension(440, 415));

        card.setBorder(new EmptyBorder(28, 38, 28, 38));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Tiêu đề
        JLabel title = new JLabel(
                "ĐĂNG NHẬP HỆ THỐNG",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(WHITE);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 9, 0);
        card.add(title, gbc);

        // Mô tả
        JLabel description = new JLabel(
                "Chào mừng bạn trở lại với Grand Hotel",
                SwingConstants.CENTER
        );

        description.setFont(new Font("Arial", Font.PLAIN, 13));
        description.setForeground(GRAY);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        card.add(description, gbc);

        // Email label
        JLabel emailLabel = createLabel("ĐỊA CHỈ EMAIL");

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 9, 0);
        card.add(emailLabel, gbc);

        // Email input
        txtEmail = new JTextField();

        styleInput(txtEmail);
        txtEmail.putClientProperty(
                "JTextField.placeholderText",
                "Nhập email của bạn..."
        );

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 23, 0);
        card.add(txtEmail, gbc);

        // Password label
        JLabel passwordLabel = createLabel("MẬT KHẨU");

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 9, 0);
        card.add(passwordLabel, gbc);

        // Password input
        txtPassword = new JPasswordField();

        styleInput(txtPassword);
        txtPassword.putClientProperty(
                "JTextField.placeholderText",
                "Nhập mật khẩu..."
        );

        passwordChar = txtPassword.getEchoChar();

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 9, 0);
        card.add(txtPassword, gbc);

        // Show password
        chkShowPassword = new JCheckBox(
                "Hiển thị mật khẩu"
        );

        chkShowPassword.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        chkShowPassword.setForeground(GRAY);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        chkShowPassword.addActionListener(e -> {

            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar(passwordChar);
            }
        });

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 22, 0);
        card.add(chkShowPassword, gbc);

        // Login button
        btnLogin = new JButton("ĐĂNG NHẬP") {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(getModel().isRollover()
                        ? new Color(240, 207, 155)
                        : GOLD);

                g2.fillRoundRect(
                        0, 0,
                        getWidth(),
                        getHeight(),
                        12, 12
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        btnLogin.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        btnLogin.setForeground(NAVY);
        btnLogin.setOpaque(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btnLogin.setPreferredSize(
                new Dimension(340, 48)
        );

        btnLogin.setMinimumSize(
                new Dimension(340, 48)
        );

        btnLogin.addActionListener(e -> login());

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipady = 12;

        card.add(btnLogin, gbc);

        return card;
    }

    // =========================================
    // FOOTER
    // =========================================

    private JPanel createFooter() {

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JLabel line = new JLabel(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        line.setForeground(new Color(222, 185, 120, 130));
        line.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel copyright = new JLabel(
                "GRAND HOTEL  |  EXPERIENCE THE LUXURY"
        );

        copyright.setFont(
                new Font("Arial", Font.PLAIN, 10)
        );

        copyright.setForeground(GRAY);
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);

        footer.add(line);
        footer.add(Box.createVerticalStrut(9));
        footer.add(copyright);

        return footer;
    }

    // =========================================
    // LABEL
    // =========================================

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        label.setForeground(GOLD);

        return label;
    }

    // =========================================
    // INPUT STYLE
    // =========================================

    private void styleInput(JTextField field) {

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setForeground(WHITE);
        field.setBackground(INPUT);
        field.setCaretColor(GOLD);

        field.setPreferredSize(
                new Dimension(340, 47)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(79, 101, 126), 1
                        ),
                        new EmptyBorder(10, 14, 10, 14)
                )
        );

        field.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusGained(
                            java.awt.event.FocusEvent e
                    ) {

                        field.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                GOLD, 2
                                        ),
                                        new EmptyBorder(
                                                9, 13, 9, 13
                                        )
                                )
                        );
                    }

                    @Override
                    public void focusLost(
                            java.awt.event.FocusEvent e
                    ) {

                        field.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                new Color(79, 101, 126), 1
                                        ),
                                        new EmptyBorder(
                                                10, 14, 10, 14
                                        )
                                )
                        );
                    }
                }
        );
    }

    // =========================================
    // ROUNDED PANEL
    // =========================================

    private static class RoundedPanel extends JPanel {

        private final int radius;
        private final Color background;
        private final Color borderColor;

        public RoundedPanel(
                int radius,
                Color background,
                Color borderColor
        ) {

            this.radius = radius;
            this.background = background;
            this.borderColor = borderColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Shadow
            g2.setColor(new Color(0, 0, 0, 45));

            g2.fillRoundRect(
                    5, 7,
                    getWidth() - 10,
                    getHeight() - 10,
                    radius, radius
            );

            // Background
            g2.setColor(background);

            g2.fillRoundRect(
                    0, 0,
                    getWidth() - 8,
                    getHeight() - 8,
                    radius, radius
            );

            // Border
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.2f));

            g2.drawRoundRect(
                    0, 0,
                    getWidth() - 8,
                    getHeight() - 8,
                    radius, radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================
    // LOGIN FUNCTION
    // =========================================

    private void login() {

        String email = txtEmail.getText().trim();

        String password = new String(
                txtPassword.getPassword()
        );

        if (email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ email và mật khẩu!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        User found = null;

        // Kiểm tra tài khoản quản trị viên
        for (User u : HotelData.admins) {

            if (u.dangNhap(email, password)) {
                found = u;
                break;
            }
        }

        // Kiểm tra tài khoản nhân viên
        if (found == null) {

            for (User u : HotelData.employees) {

                if (u.dangNhap(email, password)) {
                    found = u;
                    break;
                }
            }
        }

        // Kiểm tra tài khoản khách hàng
        if (found == null) {

            for (User u : HotelData.customers) {

                if (u.dangNhap(email, password)) {
                    found = u;
                    break;
                }
            }
        }

        // Đăng nhập thất bại
        if (found == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Email hoặc mật khẩu không chính xác!",
                    "Đăng nhập thất bại",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPassword.setText("");
            txtPassword.requestFocus();

            return;
        }

        // Đăng nhập thành công
        JOptionPane.showMessageDialog(
                this,
                "Đăng nhập thành công!\nXin chào "
                        + found.getHoTen(),
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );

        new MainFrame(found).setVisible(true);

        dispose();
    }
}
