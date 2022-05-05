package com.example.quanlybanhang.activityy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.example.quanlybanhang.fragment.FragmentAddHoaDon;
import com.example.quanlybanhang.fragment.FragmentCaiDat;
import com.example.quanlybanhang.fragment.FragmentDonViTinh;
import com.example.quanlybanhang.fragment.FragmentKhachHang;
import com.example.quanlybanhang.R;
import com.example.quanlybanhang.fragment.FragmentNhapHang;
import com.example.quanlybanhang.fragment.FragmentTaiKhoan;
import com.example.quanlybanhang.fragment.FragmentXemThem;
import com.example.quanlybanhang.fragment.Fragment_Add_SanPham;
import com.example.quanlybanhang.fragment.Fragment_ChiTietHoaDon;
import com.example.quanlybanhang.fragment.Fragment_San_Pham;
import com.example.quanlybanhang.model.HoaDon;

public class ActivityThongTin extends AppCompatActivity {
    private Toolbar toolbar;
    int  i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        setActionToolbaActivity();
        getIntentAll();
    }

    private void setActionToolbaActivity(){
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.nav_caidat);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    private void managerFragmentAll(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayoutcontentthongtin, fragment);
        transaction.commit();
    }

    private void getIntentAll(){
        Intent intent = getIntent();
        i = intent.getIntExtra("Data",10000);
        Bundle bundle;
        switch (i){
            case FragmentXemThem.ACT_CAIDAT:
                getSupportActionBar().setTitle(R.string.nav_caidat);
                managerFragmentAll(new FragmentCaiDat());
                break;
            case FragmentXemThem.ACT_BAOCAOTONGHOP:
                getSupportActionBar().setTitle(R.string.xemthem_baocaotonghop);
                break;
            case FragmentXemThem.ACT_DANHMUC:
                getSupportActionBar().setTitle(R.string.xemthem_danhmuc);
                break;
            case FragmentXemThem.ACT_DONVITINH:
                getSupportActionBar().setTitle(R.string.xemthem_donvitinh);
                managerFragmentAll(new FragmentDonViTinh());
                break;
            case FragmentXemThem.ACT_KHACHHANG:
                getSupportActionBar().setTitle(R.string.xemthem_khachhang);
                managerFragmentAll(new FragmentKhachHang());
                break;
            case FragmentXemThem.ACT_NHAPHANG:
                getSupportActionBar().setTitle(R.string.xemthem_nhaphang);
                managerFragmentAll(new FragmentNhapHang());
                break;
            case FragmentXemThem.ACT_TAIKHOAN:
                getSupportActionBar().setTitle(R.string.xemthem_taikhoan);
                managerFragmentAll(new FragmentTaiKhoan());
                break;
            case FragmentXemThem.ACT_MAYIN:
                getSupportActionBar().setTitle(R.string.xemthem_mayin);
                break;
            case FragmentXemThem.ACT_MATHANG:
                getSupportActionBar().setTitle(R.string.xemthem_mathang);
                managerFragmentAll(new Fragment_San_Pham());
                break;
            case FragmentXemThem.ACT_SHOP:
                getSupportActionBar().setTitle(R.string.nav_don_hang);
                bundle = intent.getBundleExtra("Fragment");
                managerFragmentAll((FragmentAddHoaDon) bundle.getSerializable("BundleFragment"));
                break;
            case FragmentXemThem.ACT_CHITIETHOADON:
                getSupportActionBar().setTitle(R.string.nav_don_hang);
                bundle= intent.getBundleExtra("Fragment");
                managerFragmentAll(new Fragment_ChiTietHoaDon((HoaDon)bundle.getSerializable("BundleHoaDon")));
                break;
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}