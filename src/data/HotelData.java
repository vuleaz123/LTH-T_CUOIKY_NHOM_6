package data;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelData {

    public static List<RoomType> roomTypes = new ArrayList<>();
    public static List<Room> rooms = new ArrayList<>();
    public static List<Customer> customers = new ArrayList<>();
    public static List<Employee> employees = new ArrayList<>();
    public static List<Admin> admins = new ArrayList<>();
    public static List<Booking> bookings = new ArrayList<>();
    public static List<Payment> payments = new ArrayList<>();

    static {
        createSampleData();
    }

    public static void createSampleData() {

        roomTypes.clear();
        rooms.clear();
        customers.clear();
        employees.clear();
        admins.clear();
        bookings.clear();
        payments.clear();

        RoomType standard = new RoomType(
                "LP01", "Standard", 500000, "Phòng tiêu chuẩn"
        );

        RoomType deluxe = new RoomType(
                "LP02", "Deluxe", 800000, "Phòng cao cấp"
        );

        RoomType vip = new RoomType(
                "LP03", "VIP", 1200000, "Phòng VIP"
        );

        roomTypes.add(standard);
        roomTypes.add(deluxe);
        roomTypes.add(vip);

        for (int i = 1; i <= 5; i++) {
            rooms.add(new Room(
                    "P" + (100 + i),
                    "10" + i,
                    1,
                    "Trống",
                    "Phòng tiêu chuẩn",
                    standard
            ));
        }

        for (int i = 1; i <= 5; i++) {
            rooms.add(new Room(
                    "P" + (200 + i),
                    "20" + i,
                    2,
                    "Trống",
                    "Phòng Deluxe",
                    deluxe
            ));
        }

        for (int i = 1; i <= 3; i++) {
            rooms.add(new Room(
                    "P" + (300 + i),
                    "30" + i,
                    3,
                    "Trống",
                    "Phòng VIP",
                    vip
            ));
        }

        customers.add(new Customer(
                "KH001", "Nguyễn Văn An",
                "an@gmail.com", "123",
                "0901234567", "TP. Hồ Chí Minh",
                LocalDate.of(2000, 5, 10)
        ));

        customers.add(new Customer(
                "KH002", "Trần Thị Bình",
                "binh@gmail.com", "123",
                "0912345678", "Gò Vấp",
                LocalDate.of(2001, 8, 15)
        ));

        customers.add(new Customer(
                "KH003", "Lê Minh Tuấn",
                "tuan@gmail.com", "123",
                "0987654321", "Thủ Đức",
                LocalDate.of(1999, 3, 20)
        ));

        employees.add(new Employee(
                "NV001", "Nguyễn Thị Hoa",
                "leTan@gmail.com", "123",
                "Lễ tân", "0901111222"
        ));

        admins.add(new Admin(
                "AD001", "Quản trị viên",
                "admin@gmail.com", "123",
                "Toàn quyền"
        ));

        Booking booking = new Booking(
                "DP001",
                customers.get(0),
                rooms.get(0),
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                2,
                "Đã xác nhận"
        );

        bookings.add(booking);
        rooms.get(0).setTrangThai("Đã đặt");

        payments.add(new Payment(
                "TT001", booking,
                "Tiền mặt",
                booking.getTongTien(),
                "Chưa thanh toán",
                null
        ));
    }

    public static String nextCustomerId() {
        return String.format("KH%03d", customers.size() + 1);
    }

    public static String nextRoomId() {
        return String.format("P%03d", 100 + rooms.size() + 1);
    }

    public static String nextBookingId() {
        return String.format("DP%03d", bookings.size() + 1);
    }

    public static String nextPaymentId() {
        return String.format("TT%03d", payments.size() + 1);
    }
}