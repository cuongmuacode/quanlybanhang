package com.example.quanlybanhang.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.KhachHangAdapterRecycler;
import com.example.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IAddEditModel;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.example.quanlybanhang.myinterface.IClickItemSanPham;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class FragmentKhachHang extends Fragment implements IClickItemListenerRecycer<KhachHang>,IAddEditModel<KhachHang> {
    private EditText editText;
    private RecyclerView recyclerView;
    private ImageView imageView;
    KhachHang selectKhachHang;
    MySQLiteHelper database;
    KhachHangAdapterRecycler khachHangAdapterRecycler;
    List<KhachHang> listKhachHang;;

    static Random random = new Random(System.currentTimeMillis());
    static long soMaKH = Math.abs(random.nextLong());

    List<KhachHang> filterListKhachHang = new ArrayList<>();
    public static final int SUA_KHACH_HANG = 1;
    public static final int ADD_KHACH_HANG = 2;

    IClickItemSanPham iClickItemSanPham;
    public FragmentKhachHang() {
        // Required empty public constructor
    }
    public FragmentKhachHang(IClickItemSanPham iClickItemSanPham) {
        // Required empty public constructor
        this.iClickItemSanPham = iClickItemSanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_khac_hang, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_khachhang_search);
        recyclerView = view.findViewById(R.id.recycler_KhachHang);
        imageView = view.findViewById(R.id.imageview_khachhang_sort);
        database = new MySQLiteHelper(getContext());

        registerForContextMenu(recyclerView);
        xuLyRecyclerView();
        xulyEditText();
        xuLySort();
    }



    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,R.id.menu_right_add,1,R.string.nav_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                replaceFragment(new FragmentAddKhachHang(FragmentKhachHang.this,ADD_KHACH_HANG));
                return true;
            }
        });
        menuItem.setIcon(R.drawable.ic_baseline_add_24);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull  View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_model_xoa){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.nav_model_xoa);
            builder.setMessage("Bạn có chắc không ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    database.deleteKhachHang(selectKhachHang);
                    if (!selectKhachHang.getMaKH().equals("MaKH01"))
                        listKhachHang.remove(selectKhachHang);
                    if(filterListKhachHang.size()>0) {
                        if (!selectKhachHang.getMaKH().equals("MaKH01")) {
                            filterListKhachHang.remove(selectKhachHang);
                            recyclerView.setAdapter(new KhachHangAdapterRecycler(filterListKhachHang, FragmentKhachHang.this));

                        }
                    }
                    khachHangAdapterRecycler.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else if (item.getItemId()==R.id.menu_model_sua)
            replaceFragment(new FragmentAddKhachHang(this,SUA_KHACH_HANG));

        return super.onContextItemSelected(item);
    }

    @Override
    public void onClickItemModel(KhachHang khachHang ) {
         selectKhachHang = khachHang;
    }

    @Override
    public void onClickChiTietModel(KhachHang khachHang) {
            if(iClickItemSanPham!=null) {
                iClickItemSanPham.onClickKhachHang(khachHang);
                getActivity().onBackPressed();
                return;
            }
            replaceFragment(new Fragment_ChiTietKhachHang(khachHang));
    }

    @Override
    public boolean processModel(KhachHang khachHang,int i) {
        if(i == SUA_KHACH_HANG) {
            khachHang.setMaKH(selectKhachHang.getMaKH());
            database.updateKhachHang(khachHang);
            listKhachHang.addAll(database.getListKhachHang());
            khachHangAdapterRecycler.notifyDataSetChanged();
            return true;
        }
        else if(i == ADD_KHACH_HANG){
            String stringMaKH = "MaKH"+soMaKH;
            khachHang.setMaKH(stringMaKH);
            if(database.addKhachHang(khachHang)) soMaKH = Math.abs(random.nextLong());
            else {
                soMaKH = Math.abs(random.nextLong());
                return false;
            }
            listKhachHang.addAll(database.getListKhachHang());
            khachHangAdapterRecycler.notifyDataSetChanged();
            return true;
        }
        return false;
    }
    private void xulyEditText() {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view == editText) {
                    if (b) {
                        // Open keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                    } else {
                        // Close keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()) {
                    khachHangAdapterRecycler = new KhachHangAdapterRecycler(listKhachHang,FragmentKhachHang.this);
                    recyclerView.setAdapter(khachHangAdapterRecycler);
                    khachHangAdapterRecycler.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();
                filterListKhachHang.clear();
                if(search.isEmpty()) {
                    khachHangAdapterRecycler = new KhachHangAdapterRecycler(listKhachHang,FragmentKhachHang.this);
                    recyclerView.setAdapter(khachHangAdapterRecycler);
                }
                else{
                    for(KhachHang khachHang : listKhachHang){
                        if(khachHang.getTenKH().contains(search)||
                                (khachHang.getEmail()).contains(search))
                            filterListKhachHang.add(khachHang);
                    }
                    recyclerView.setAdapter(new KhachHangAdapterRecycler(filterListKhachHang,FragmentKhachHang.this));
                }
            }
        });
    }

    private void xuLySort() {
        imageView.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(listKhachHang, new Comparator<KhachHang>() {
                        @Override
                        public int compare(KhachHang khachHang, KhachHang khachHang1) {
                            int a = khachHang.getTenKH().compareTo(khachHang1.getTenKH());
                            if(a!=0) return a;
                            else return   khachHang.getEmail().compareTo(khachHang1.getEmail());
                        }
                    });
                    i=2;
                }
                else if((i==2)){
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(listKhachHang, new Comparator<KhachHang>() {
                        @Override
                        public int compare(KhachHang khachHang, KhachHang khachHang1) {
                            int a = khachHang1.getTenKH().compareTo(khachHang.getTenKH());
                            if(a!=0) return a;
                            else return  khachHang1.getEmail().compareTo(khachHang.getEmail());
                        }
                    });
                    i=3;
                }
                else if(i==3){
                    imageView.setImageResource(R.drawable.ic_baseline_sort_24);
                    listKhachHang.clear();
                    listKhachHang.addAll(database.getListKhachHang());
                    i=1;
                }
                khachHangAdapterRecycler.notifyDataSetChanged();
            }
        });
    }
    public void xuLyRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        listKhachHang = database.getListKhachHang();
        khachHangAdapterRecycler = new KhachHangAdapterRecycler(listKhachHang, this);
        recyclerView.setAdapter(khachHangAdapterRecycler);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}