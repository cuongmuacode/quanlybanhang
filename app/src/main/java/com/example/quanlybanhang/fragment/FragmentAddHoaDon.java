package com.example.quanlybanhang.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.MatHangAdapterRecycler;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.HoaDon;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.example.quanlybanhang.myinterface.IClickItemSanPham;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class FragmentAddHoaDon extends Fragment implements IClickItemSanPham,Serializable,IClickItemListenerRecycer<SanPham> {
    List<SanPham> sanPhamList = new ArrayList<>();
    RecyclerView recyclerView;
    AppCompatButton appCompatButton;
    MatHangAdapterRecycler matHangAdapterRecycler;
    public SanPham selectSanPham;
    public KhachHang selectKhachhang;
    public HoaDon hoaDon;
    MySQLiteHelper database;
    TextView textSoHoaDon;
    TextView tenKhachHang;
    TextView textTenNhanVien;
    TextView textTongTien;
    ImageView chonTenKhachHang;
    Random random = new Random(System.currentTimeMillis());
    int soHD;
    long triGia = 0;
    String nhanVien = "Cường";


    public FragmentAddHoaDon() {
        soHD =  Math.abs(random.nextInt());
    }
    public FragmentAddHoaDon(HoaDon hoaDon){
        this.hoaDon = hoaDon;
    }

    public FragmentAddHoaDon(List<SanPham> sanPhamList, SanPham selectSanPham, KhachHang selectKhachhang,
                             HoaDon hoaDon, Random random, int soHD, String nhanVien) {
        this.sanPhamList = sanPhamList;
        this.selectSanPham = selectSanPham;
        this.selectKhachhang = selectKhachhang;
        this.hoaDon = hoaDon;
        this.random = random;
        this.soHD = soHD;
        this.nhanVien = nhanVien;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycer_hoadon_add);
        appCompatButton = view.findViewById(R.id.add_hoadon_donhang);
        textSoHoaDon = view.findViewById(R.id.addSoHoaDon);
        chonTenKhachHang = view.findViewById(R.id.chon_khachhang_hoadon);
        tenKhachHang = view.findViewById(R.id.tenkhachhang_hoadon);
        textTongTien = view.findViewById(R.id.tongtienhoadon);
        textTenNhanVien = view.findViewById(R.id.tenNhanVienHoaDon);
        database = new MySQLiteHelper(getContext());
        xulyall();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {
        MenuItem menuItem = menu.add(1,1,1,"Xóa");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setTitle(R.string.nav_model_xoa);
        menuItem.setIcon(R.drawable.ic_baseline_delete_24);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getActivity().setResult(getActivity().RESULT_OK);
                sanPhamList.clear();
                selectKhachhang = database.getKhachHang("MaKH01");
                tenKhachHang.setText("Tên khách hàng : "+selectKhachhang.getTenKH());
                nhanVien = "Cường";
                textTongTien.setText("$0");
                matHangAdapterRecycler.notifyDataSetChanged();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreateContextMenu( ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
        MenuItem menuItemSoLuong = menu.findItem(R.id.menu_model_soluong);
        MenuItem menuItemSua = menu.findItem(R.id.menu_model_sua);
        MenuItem menuItemXoa = menu.findItem(R.id.menu_model_xoa);
        menuItemSoLuong.setVisible(true);
        menuItemSua.setVisible(false);
        menuItemXoa.setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull  MenuItem item) {
        if(item.getItemId()==R.id.menu_model_soluong)
            replaceFragment(new FragmentThemSoLuong(selectSanPham));
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClickSanPham(SanPham sanPham) {
        if(sanPham !=null)
            if (sanPhamList.isEmpty()) {
                sanPham.setSoLuongSP(1);
                sanPhamList.add(sanPham);
            } else {
                for (SanPham sp : sanPhamList) {
                    if (sp.getMaSP().equals(sanPham.getMaSP()))
                        return;
                }
                sanPham.setSoLuongSP(1);
                sanPhamList.add(sanPham);
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.gc();
        FragmentBanHang.iClickItemSanPham =  new FragmentAddHoaDon(FragmentAddHoaDon.this.sanPhamList,
                FragmentAddHoaDon.this.selectSanPham,FragmentAddHoaDon.this.selectKhachhang,FragmentAddHoaDon.this.hoaDon,
                FragmentAddHoaDon.this.random,FragmentAddHoaDon.this.soHD,FragmentAddHoaDon.this.nhanVien);
    }

    @Override
    public void onClickKhachHang(KhachHang khachHang) {
        selectKhachhang = khachHang;
    }

    @Override
    public void onClickItemModel(SanPham sanPham) {
        getActivity().setResult(getActivity().RESULT_CANCELED);
        sanPhamList.remove(sanPham);
        if(!sanPhamList.isEmpty()) {
            triGia = 0;
            for (SanPham sp : sanPhamList)
                triGia += sp.getSoLuongSP() * sp.getGiaSP();
            textTongTien.setText("$" + triGia);
        }
        else
            textTongTien.setText("$0");
        matHangAdapterRecycler.notifyDataSetChanged();
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {
        selectSanPham = sanPham;
    }



    private void xulyall() {
        matHangAdapterRecycler =  new MatHangAdapterRecycler(
                FragmentAddHoaDon.this,sanPhamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(matHangAdapterRecycler);
        registerForContextMenu(recyclerView);
        textSoHoaDon.setText("Số hóa đơn : "+soHD);
        textTenNhanVien.setText("Tên nhân viên : "+nhanVien);

        chonTenKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentKhachHang(FragmentAddHoaDon.this));
            }
        });
        tenKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentKhachHang(FragmentAddHoaDon.this));
            }
        });
        if(selectKhachhang==null) selectKhachhang = database.getKhachHang("MaKH01");
        tenKhachHang.setText("Tên khách hàng : "+selectKhachhang.getTenKH());

        if(!sanPhamList.isEmpty()) {
            triGia = 0;
            for (SanPham sp : sanPhamList)
                triGia += sp.getSoLuongSP() * sp.getGiaSP();
            textTongTien.setText("$" + triGia);
        }
        else textTongTien.setText("$0");

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            int processingClick = 0;
            @Override
            public void onClick(View view) {

                if(!sanPhamList.isEmpty()){
                    hoaDon = new HoaDon(
                            "HD" + soHD,
                            System.currentTimeMillis()+"",
                            selectKhachhang.getMaKH(),
                            "MaNV01",
                            triGia,
                            sanPhamList
                    );
                    if(database.addHoaDon(hoaDon)) {
                        Toast.makeText(getContext(), "Thành công !!", Toast.LENGTH_SHORT).show();
                        for(SanPham sp:sanPhamList){
                            int a = database.getSanPham(sp.getMaSP()).getSoLuongSP()-sp.getSoLuongSP();
                            SanPham sanPham = database.getSanPham(sp.getMaSP());
                            sanPham.setSoLuongSP(a);
                            database.updateSanPham(sanPham);
                        }
                        getActivity().setResult(getActivity().RESULT_OK);
                        getActivity().onBackPressed();
                    }
                    else {
                        soHD =  Math.abs(random.nextInt());
                        Toast.makeText(getContext(), "Thử lại xem !!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                if (processingClick < 5) {
                    processingClick++;
                    Toast.makeText(getContext(), "Chưa chọn sản phảm !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}