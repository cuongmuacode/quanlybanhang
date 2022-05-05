package com.example.quanlybanhang.myinterface;

import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.model.SanPham;

public interface IClickItemSanPham {
    void onClickSanPham(SanPham sanPham);
    void onClickKhachHang(KhachHang khachHang);
}
