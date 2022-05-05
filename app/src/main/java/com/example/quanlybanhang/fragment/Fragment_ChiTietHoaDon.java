package com.example.quanlybanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.HoaDon;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.model.SanPham;

import java.io.Serializable;
import java.util.List;

public class Fragment_ChiTietHoaDon extends Fragment implements Serializable {
    HoaDon hoaDon;
    List<SanPham> sanPhamList;
    TextView textSoHoaDon;
    TextView tenKhachHang;
    TextView textTenNhanVien;
    TextView textTongTien;
    RecyclerView recyclerView;
    SanPhamAdapterRecycler sanPhamAdapterRecycler;
    MySQLiteHelper database;


    public Fragment_ChiTietHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
        this.sanPhamList = hoaDon.getSanPhamList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textSoHoaDon = view.findViewById(R.id.ChiTiet_SoHoaDon);
        tenKhachHang = view.findViewById(R.id.ChiTiet_tenkhachhang_hoadon);
        textTenNhanVien = view.findViewById(R.id.ChiTiet_tenNhanVienHoaDon);
        textTongTien = view.findViewById(R.id.ChiTiet_tongtienhoadon);
        recyclerView = view.findViewById(R.id.ChiTiet_recycer_hoadon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        sanPhamAdapterRecycler = new SanPhamAdapterRecycler(sanPhamList);
        recyclerView.setAdapter(sanPhamAdapterRecycler);
        textSoHoaDon.setText("Số hóa đơn : "+hoaDon.getSoHD());
        textTenNhanVien.setText("Tên nhân viên : "+"Cường");
        database = new MySQLiteHelper(getContext());
        KhachHang khachHang = database.getKhachHang(hoaDon.getMaKH());
        tenKhachHang.setText("Tên khách hàng : "+khachHang.getTenKH());
        if(!sanPhamList.isEmpty()) {
            long triGia = 0;
            for (SanPham sp : sanPhamList)
                triGia += sp.getSoLuongSP() * sp.getGiaSP();
            textTongTien.setText("$" + triGia);
        }
        else textTongTien.setText("$0");


    }
}