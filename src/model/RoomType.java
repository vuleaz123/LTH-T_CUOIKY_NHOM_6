package model;

public class RoomType {
    private String maLoaiPhong;
    private String tenLoaiPhong;
    private double giaPhong;
    private String moTa;

    public RoomType(String maLoaiPhong, String tenLoaiPhong,
                    double giaPhong, String moTa) {
        this.maLoaiPhong = maLoaiPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.giaPhong = giaPhong;
        this.moTa = moTa;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public double getGiaPhong() {
        return giaPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}