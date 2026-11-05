package model;

public class Admin extends User {
    private String maQuanTri;
    private String quyenHan;

    public Admin(String maQuanTri, String hoTen,
                 String email, String matKhau,
                 String quyenHan) {

        super(maQuanTri, hoTen, email,
                matKhau, "Quản trị viên", true);

        this.maQuanTri = maQuanTri;
        this.quyenHan = quyenHan;
    }

    public String getMaQuanTri() {
        return maQuanTri;
    }

    public String getQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    public void quanLyTaiKhoan() {
        System.out.println("Đang quản lý tài khoản...");
    }

    public void quanLyHeThong() {
        System.out.println("Đang quản lý hệ thống...");
    }
}