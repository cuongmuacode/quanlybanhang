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
import com.example.quanlybanhang.model.SanPham;

import org.w3c.dom.Text;


public class Fragment_ChiTietSanPham extends Fragment {
    private SanPham sanPham;

    public Fragment_ChiTietSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewName = view.findViewById(R.id.chitietSanPham_TenSP);
        TextView textViewChiTietSP = view.findViewById(R.id.chitietSanPhamMota);
        TextView textViewThongTin = view.findViewById(R.id.chitietSanPhamThongTin);
        textViewName.setText(Html.fromHtml(getString(R.string.chitietSanPham_TenSP)+" : "+sanPham.getTenSP()));

        textViewChiTietSP.setText(Html.fromHtml(getString(R.string.chitietSanPham_MoTa)+" : "+sanPham.getChiTietSP()));

        textViewThongTin.setText(Html.fromHtml(getString(R.string.chitietSanPham_NuocSX)+" : "+sanPham.getNuocSX()+
                "<br/>"+getString(R.string.chitietSanPham_DonViTinh)+" : "+sanPham.getDonViTinh()+
                "<br/>"+getString(R.string.chitietSanPham_Gia)+" : "+sanPham.getGiaSP()+
                "<br/>"+getString(R.string.chitietSanPham_SoLuong)+" : "+sanPham.getSoLuongSP()));
    }
}