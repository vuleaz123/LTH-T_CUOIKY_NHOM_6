package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String maKhachHang;
    private String soDienThoai;
    private String diaChi;
    private LocalDate ngaySinh;

    public Customer(String maKhachHang, String hoTen,
                    String email, String matKhau,
                    String soDienThoai, String diaChi,
                    LocalDate ngaySinh) {

        super(maKhachHang, hoTen, email,
                matKhau, "Khách hàng", true);

        this.maKhachHang = maKhachHang;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}