package model;

public class Employee extends User {
    private String maNhanVien;
    private String chucVu;
    private String soDienThoai;

    public Employee(String maNhanVien, String hoTen,
                    String email, String matKhau,
                    String chucVu, String soDienThoai) {

        super(maNhanVien, hoTen, email,
                matKhau, "Nhân viên", true);

        this.maNhanVien = maNhanVien;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getChucVu() {
        return chucVu;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}