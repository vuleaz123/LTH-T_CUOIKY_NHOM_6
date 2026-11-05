package model;

import java.time.LocalDate;

public class Payment {
    private String maThanhToan;
    private Booking datPhong;
    private String phuongThuc;
    private double soTien;
    private String trangThai;
    private LocalDate ngayThanhToan;

    public Payment(String maThanhToan, Booking datPhong,
                   String phuongThuc, double soTien,
                   String trangThai, LocalDate ngayThanhToan) {

        this.maThanhToan = maThanhToan;
        this.datPhong = datPhong;
        this.phuongThuc = phuongThuc;
        this.soTien = soTien;
        this.trangThai = trangThai;
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public Booking getDatPhong() {
        return datPhong;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public double getSoTien() {
        return soTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public LocalDate getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void thanhToan() {
        this.trangThai = "Đã thanh toán";
        this.ngayThanhToan = LocalDate.now();
    }

    public void huyThanhToan() {
        this.trangThai = "Đã hủy";
    }
}