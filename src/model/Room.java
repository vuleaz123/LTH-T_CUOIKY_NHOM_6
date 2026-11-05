package model;

public class Room {
    private String maPhong;
    private String soPhong;
    private int tang;
    private String trangThai;
    private String moTa;
    private RoomType loaiPhong;

    public Room(String maPhong, String soPhong,
                int tang, String trangThai,
                String moTa, RoomType loaiPhong) {

        this.maPhong = maPhong;
        this.soPhong = soPhong;
        this.tang = tang;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.loaiPhong = loaiPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public int getTang() {
        return tang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public RoomType getLoaiPhong() {
        return loaiPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setLoaiPhong(RoomType loaiPhong) {
        this.loaiPhong = loaiPhong;
    }
}