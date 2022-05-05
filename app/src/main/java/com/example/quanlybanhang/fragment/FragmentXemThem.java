package com.example.quanlybanhang.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.activityy.ActivityThongTin;

public class FragmentXemThem extends Fragment implements View.OnClickListener{
    TextView textViewKhachHang;
    TextView textViewTaiKhoan;
    TextView textViewDonViTinh;
    TextView textViewMayIn;
    TextView textViewDanhMuc;
    TextView textViewBaoCaoTongHop;
    TextView textViewNhapHang;
    TextView textViewCaiDat;
    TextView textViewMatHang;

    public static final int ACT_KHACHHANG = 1;
    public static final int ACT_TAIKHOAN = 2;
    public static final int ACT_DONVITINH = 3;
    public static final int ACT_MAYIN = 4;
    public static final int ACT_DANHMUC = 5;
    public static final int ACT_BAOCAOTONGHOP = 6;
    public static final int ACT_NHAPHANG = 7;
    public static final int ACT_CAIDAT = 8;
    public static final int ACT_MATHANG = 9;
    public static final int ACT_SHOP = 10;
    public static final int ACT_CHITIETHOADON = 11;




    public FragmentXemThem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_xem_them, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWidget(view);
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        Intent intent;
        switch (i){
            case R.id.xemthem_nhaphang:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_NHAPHANG);
                startActivity(intent);
                break;
            case R.id.xem_mayin:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_MAYIN);
                startActivity(intent);
                break;
            case R.id.xemthem_caidat:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_CAIDAT);
                startActivity(intent);
                break;
            case R.id.xemthem_baocaotonghop:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_BAOCAOTONGHOP);
                startActivity(intent);
                break;
            case R.id.xemthem_danhmuc:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_DANHMUC);
                startActivity(intent);
                break;
            case R.id.xemthem_donvitinh:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_DONVITINH);
                startActivity(intent);
                break;
            case R.id.xemthem_taikhoan:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_TAIKHOAN);
                startActivity(intent);
                break;
            case R.id.xemthem_khachang:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_KHACHHANG);
                startActivity(intent);
                break;
            case R.id.xemthem_mathang:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_MATHANG);
                startActivity(intent);
                break;
        }
    }
    private void getWidget(View view) {
        textViewKhachHang = view.findViewById(R.id.xemthem_khachang);
        textViewTaiKhoan = view.findViewById(R.id.xemthem_taikhoan);
        textViewDonViTinh = view.findViewById(R.id.xemthem_donvitinh);
        textViewMayIn = view.findViewById(R.id.xem_mayin);
        textViewDanhMuc = view.findViewById(R.id.xemthem_danhmuc);
        textViewBaoCaoTongHop = view.findViewById(R.id.xemthem_baocaotonghop);
        textViewNhapHang = view.findViewById(R.id.xemthem_nhaphang);
        textViewCaiDat = view.findViewById(R.id.xemthem_caidat);
        textViewMatHang = view.findViewById(R.id.xemthem_mathang);


        textViewKhachHang.setOnClickListener(this);
        textViewTaiKhoan.setOnClickListener(this);
        textViewDonViTinh.setOnClickListener(this);
        textViewMayIn.setOnClickListener(this);
        textViewDanhMuc.setOnClickListener(this);
        textViewBaoCaoTongHop.setOnClickListener(this);
        textViewNhapHang.setOnClickListener(this);
        textViewCaiDat.setOnClickListener(this);
        textViewMatHang.setOnClickListener(this);

    }
}