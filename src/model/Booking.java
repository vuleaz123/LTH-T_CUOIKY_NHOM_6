package model;

import java.time.LocalDate;

public class Booking {
    private String maDatPhong;
    private Customer khachHang;
    private Room phong;
    private LocalDate ngayDat;
    private LocalDate ngayNhan;
    private LocalDate ngayTra;
    private int soLuongKhach;
    private double tongTien;
    private String trangThai;

    public Booking(String maDatPhong, Customer khachHang,
                   Room phong, LocalDate ngayDat,
                   LocalDate ngayNhan, LocalDate ngayTra,
                   int soLuongKhach, String trangThai) {

        this.maDatPhong = maDatPhong;
        this.khachHang = khachHang;
        this.phong = phong;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.soLuongKhach = soLuongKhach;
        this.trangThai = trangThai;

        tinhTongTien();
    }

    public void tinhTongTien() {
        long soNgay = java.time.temporal.ChronoUnit.DAYS
                .between(ngayNhan, ngayTra);

        if (soNgay <= 0) soNgay = 1;

        tongTien = soNgay * phong.getLoaiPhong().getGiaPhong();
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public Customer getKhachHang() {
        return khachHang;
    }

    public Room getPhong() {
        return phong;
    }

    public LocalDate getNgayDat() {
        return ngayDat;
    }

    public LocalDate getNgayNhan() {
        return ngayNhan;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public int getSoLuongKhach() {
        return soLuongKhach;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}