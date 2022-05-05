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

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.adapter.KhachHangAdapterRecycler;
import com.example.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IAddEditModel;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class Fragment_San_Pham extends Fragment implements IClickItemListenerRecycer<SanPham>, IAddEditModel<SanPham> {
    private EditText editText;
    private RecyclerView recyclerView;
    private ImageView imageView;
    SanPham selectSanPham;
    MySQLiteHelper database;
    SanPhamAdapterRecycler sanPhamAdapterRecycler;
    List<SanPham> listSanPham;
    List<SanPham> filterListSanPham = new ArrayList<>();
    static Random random = new Random(System.currentTimeMillis());
    static long soMaSP = Math.abs(random.nextLong());

    public static final int SUA_SAN_PHAM = 1;
    public static final int ADD_SAN_PHAM = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment__san__pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_sanpham_search);
        recyclerView = view.findViewById(R.id.recycler_SanPham);
        imageView = view.findViewById(R.id.imageview_sanpham_sort);
        xuLyRecyclerView();
        xulyEditText();
        xuLySort();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,R.id.menu_right_add,1,R.string.nav_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                replaceFragment(new Fragment_Add_SanPham(Fragment_San_Pham.this,ADD_SAN_PHAM));
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
                        database.deleteSanPham(selectSanPham);
                    listSanPham.remove(selectSanPham);
                    if(filterListSanPham != null) {
                        filterListSanPham.remove(selectSanPham);
                        recyclerView.setAdapter(new SanPhamAdapterRecycler(Fragment_San_Pham.this,filterListSanPham));
                        sanPhamAdapterRecycler.notifyDataSetChanged();
                    }

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
            replaceFragment(new Fragment_Add_SanPham(this,SUA_SAN_PHAM));
        return super.onContextItemSelected(item);
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();
                if(search.isEmpty()) {
                    sanPhamAdapterRecycler = new SanPhamAdapterRecycler(Fragment_San_Pham.this,listSanPham);
                    recyclerView.setAdapter(sanPhamAdapterRecycler);

                }
                else{
                    filterListSanPham.clear();
                    for(SanPham sanPham : listSanPham){
                        if(sanPham.getTenSP().contains(search)||
                                (sanPham.getGiaSP()+"").contains(search))
                            filterListSanPham.add(sanPham);
                    }
                    recyclerView.setAdapter(new SanPhamAdapterRecycler(Fragment_San_Pham.this,filterListSanPham));
                }
                sanPhamAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

    private void xuLyRecyclerView() {
        database = new MySQLiteHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        listSanPham = database.getListSanPham();
        sanPhamAdapterRecycler = new SanPhamAdapterRecycler(this,listSanPham);
        recyclerView.setAdapter(sanPhamAdapterRecycler);
        registerForContextMenu(recyclerView);
    }

    private void xuLySort() {
        imageView.setOnClickListener(new View.OnClickListener() {
           int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(listSanPham, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham sanPham1) {
                            int a = sanPham.getTenSP().compareTo(sanPham1.getTenSP());
                            if(a!=0) return a;
                            else return  sanPham.getSoLuongSP() - sanPham1.getSoLuongSP();
                        }
                    });
                    i=2;
                }
                else if(i==2){
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(listSanPham, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham sanPham1) {
                            int a = sanPham1.getTenSP().compareTo(sanPham.getTenSP());
                            if(a!=0) return a;
                            else return  sanPham1.getSoLuongSP() - sanPham.getSoLuongSP();
                        }
                    });
                    i=3;
                }
                else if(i==3){
                    imageView.setImageResource(R.drawable.ic_baseline_sort_24);
                    listSanPham.clear();
                    listSanPham.addAll(database.getListSanPham());
                    i=1;
                }
                sanPhamAdapterRecycler.notifyDataSetChanged();
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


    @Override
    public boolean processModel(SanPham sanPham, int i) {
        if(i == SUA_SAN_PHAM) {
            sanPham.setMaSP(selectSanPham.getMaSP());
            database.updateSanPham(sanPham);
            listSanPham.addAll(database.getListSanPham());
            sanPhamAdapterRecycler.notifyDataSetChanged();
            return true;
        }
        else if(i == ADD_SAN_PHAM){
            String stringMaSP = "MaSP"+soMaSP;
            sanPham.setMaSP(stringMaSP);
            if(database.addSanPham(sanPham)) soMaSP = Math.abs(random.nextLong());
            else {
                Log.i("arr","i == "+soMaSP);
                soMaSP = Math.abs(random.nextLong());
                return false;
            }
            listSanPham.addAll(database.getListSanPham());
            sanPhamAdapterRecycler.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public void onClickItemModel(SanPham sanPham) {
        selectSanPham = sanPham;
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {
        replaceFragment(new Fragment_ChiTietSanPham(sanPham));
    }
}