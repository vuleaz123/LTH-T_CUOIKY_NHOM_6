import data.HotelData;
import ui.LoginFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // 1. Khởi tạo dữ liệu mẫu
        HotelData.createSampleData();

        // 2. Thiết lập giao diện
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Khởi chạy màn hình đăng nhập
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}