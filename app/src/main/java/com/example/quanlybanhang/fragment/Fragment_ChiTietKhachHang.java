package com.example.quanlybanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.KhachHang;


public class Fragment_ChiTietKhachHang extends Fragment {

    private KhachHang khachHang;
    public Fragment_ChiTietKhachHang(KhachHang khachHang) {
            this.khachHang = khachHang;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_khach_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewName = view.findViewById(R.id.chiTietKhachHang_Name);
        TextView textViewDiaChi = view.findViewById(R.id.chiTietKhachHang_DiaChi);
        TextView textViewEmail = view.findViewById(R.id.chiTietKhachHang_Email);
        TextView textViewGhiChu = view.findViewById(R.id.chiTietKhachHang_GhiChu);
        TextView textViewSoDT = view.findViewById(R.id.chiTietKhachHang_SODT);

        textViewName.setText(Html.fromHtml("<b>Tên khách hàng</b> : "+khachHang.getTenKH()));
        textViewDiaChi.setText(Html.fromHtml("<b>Địa chỉ</b> : "+khachHang.getDiaChi()));
        textViewEmail.setText(Html.fromHtml("<b>Email</b> : "+khachHang.getEmail()));
        textViewSoDT.setText(Html.fromHtml("<b>Số điện thoại</b> : "+khachHang.getSoDT()));
        textViewGhiChu.setText(Html.fromHtml("<b>Ghi chú</b> : "+khachHang.getGhiChu()));

    }
}