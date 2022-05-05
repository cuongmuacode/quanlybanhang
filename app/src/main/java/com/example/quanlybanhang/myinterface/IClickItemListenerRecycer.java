package com.example.quanlybanhang.myinterface;

import com.example.quanlybanhang.model.KhachHang;

public interface IClickItemListenerRecycer<T> {
    void onClickItemModel(T t);
    void onClickChiTietModel(T t);
}
