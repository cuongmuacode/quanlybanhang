package com.example.quanlybanhang.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class SanPham implements Serializable{
    private String maSP,tenSP,donViTinh,nuocSX,chiTietSP;
    private long giaSP;
    private int soLuongSP;

    public SanPham(String maSP, String tenSP, String donViTinh, String nuocSX, String chiTietSP, long giaSP, int soLuongSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donViTinh = donViTinh;
        this.nuocSX = nuocSX;
        this.chiTietSP = chiTietSP;
        this.giaSP = giaSP;
        this.soLuongSP = soLuongSP;
    }

    public SanPham() {
    }

    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getNuocSX() {
        return nuocSX;
    }

    public void setNuocSX(String nuocSX) {
        this.nuocSX = nuocSX;
    }

    public String getChiTietSP() {
        return chiTietSP;
    }

    public void setChiTietSP(String chiTietSP) {
        this.chiTietSP = chiTietSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanPham)) return false;
        SanPham sanPham = (SanPham) o;
        return getGiaSP() == sanPham.getGiaSP() &&
                getSoLuongSP() == sanPham.getSoLuongSP() &&
                getMaSP().equals(sanPham.getMaSP()) &&
                getTenSP().equals(sanPham.getTenSP()) &&
                getDonViTinh().equals(sanPham.getDonViTinh()) &&
                getNuocSX().equals(sanPham.getNuocSX()) &&
                getChiTietSP().equals(sanPham.getChiTietSP());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaSP(), getTenSP(), getDonViTinh(), getNuocSX(), getChiTietSP(), getGiaSP(), getSoLuongSP());
    }


}
