package com.example.quanlybanhang.model;

import java.io.Serializable;
import java.util.List;

public class HoaDon implements Serializable {

    private String soHD,ngayHD,maKH,maNV;
    private Long triGia;
    private List<SanPham> sanPhamList;

    public HoaDon() {
    }

    public HoaDon(String soHD, String ngayHD, String maKH, String maNV, long triGia, List<SanPham> sanPhamList) {
        this.soHD = soHD;
        this.ngayHD = ngayHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.triGia = triGia;
        this.sanPhamList = sanPhamList;
    }

    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public String getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(String ngayHD) {
        this.ngayHD = ngayHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Long getTriGia() {
        return triGia;
    }

    public void setTriGia(Long triGia) {
        this.triGia = triGia;
    }

    public List<SanPham> getSanPhamList() {
        return sanPhamList;
    }

    public void setSanPhamList(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }
}
