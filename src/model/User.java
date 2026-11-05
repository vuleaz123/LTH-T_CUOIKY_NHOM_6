package model;

public class User {
    protected String maNguoiDung;
    protected String hoTen;
    protected String email;
    protected String matKhau;
    protected String vaiTro;
    protected boolean trangThai;

    public User(String maNguoiDung, String hoTen,
                String email, String matKhau,
                String vaiTro, boolean trangThai) {
        this.maNguoiDung = maNguoiDung;
        this.hoTen = hoTen;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;

    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public boolean dangNhap(String email, String matKhau) {
        return this.email.equals(email)
                && this.matKhau.equals(matKhau)
                && trangThai;
    }

    public void dangXuat() {
        System.out.println("Đã đăng xuất!");
    }

    public void doiMatKhau(String matKhauMoi) {
        this.matKhau = matKhauMoi;
    }
}