 package com.example.quanlybanhang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.quanlybanhang.activityy.ActivityThongTin;
import com.example.quanlybanhang.adapter.MatHangAdapterRecycler;
import com.example.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.example.quanlybanhang.myinterface.IClickItemSanPham;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentBanHang extends Fragment implements IClickItemListenerRecycer<SanPham>{
    private RecyclerView recyclerViewBanHang;
    private MatHangAdapterRecycler matHangAdapterRecycler;
    private EditText editText;
    private ImageView imageView;
    List<SanPham> filterListSanPham = new ArrayList<>();

    MySQLiteHelper database;
    List<SanPham> listSanPham = new ArrayList<>();
    static IClickItemSanPham iClickItemSanPham = new FragmentAddHoaDon();

    public FragmentBanHang() {
        // Required empty public constructor
    }

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.i("cuong","3");
            if(result.getResultCode()==getActivity().RESULT_OK)
                iClickItemSanPham = new FragmentAddHoaDon();
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ban_hang,container,false);
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_sanpham_search_banhang);
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
        imageView = view.findViewById(R.id.imageview_sanpham_sort_banhang);
        recyclerViewBanHang = view.findViewById(R.id.recyclerBanHang);
        recyclerViewBanHang.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        recyclerViewBanHang.addItemDecoration(new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL));
        database = new MySQLiteHelper(getContext());
        listSanPham = database.getListSanPham();
        matHangAdapterRecycler = new MatHangAdapterRecycler(this,listSanPham);
        recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
        xulyEditText();
        xuLySort();
     }


    @Override
    public void onStart() {
        super.onStart();
        listSanPham.clear();
        listSanPham.addAll(database.getListSanPham());
        matHangAdapterRecycler.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itemMenuMuaHang = menu.add(1,R.id.menu_right_muahang,1,R.string.nav_muahang);
        itemMenuMuaHang.setIcon(R.drawable.ic_baseline_add_shopping_cart_24);
        itemMenuMuaHang.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemMenuMuaHang.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getContext(), ActivityThongTin.class);
                intent.putExtra("Data",FragmentXemThem.ACT_SHOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BundleFragment",(FragmentAddHoaDon) iClickItemSanPham);
                intent.putExtra("Fragment",bundle);
                intentActivityResultLauncher.launch(intent);
                return false;
            }
        });
    }


    @Override
    public void onClickItemModel(SanPham sanPham) {
        if(sanPham.getSoLuongSP()<=0) Toast.makeText(getContext(),"Hết hàng !!!",Toast.LENGTH_SHORT).show();
        else
            iClickItemSanPham.onClickSanPham(sanPham);
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {

    }
    private void xulyEditText() {
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
                    matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                    recyclerViewBanHang.setAdapter(matHangAdapterRecycler);

                }
                else{
                    filterListSanPham.clear();
                    for(SanPham sanPham : listSanPham){
                        if(sanPham.getTenSP().contains(search)||
                                (sanPham.getGiaSP()+"").contains(search))
                            filterListSanPham.add(sanPham);
                    }
                    recyclerViewBanHang.setAdapter(new MatHangAdapterRecycler(FragmentBanHang.this,filterListSanPham));
                }
                matHangAdapterRecycler.notifyDataSetChanged();
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
                else if(i==2) {
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
                else if(i == 3){
                    imageView.setImageResource(R.drawable.ic_baseline_sort_24);
                    listSanPham.clear();
                    listSanPham.addAll(database.getListSanPham());
                    i=1;
                }
                matHangAdapterRecycler.notifyDataSetChanged();
            }
        });
    }



}