package com.example.quanlybanhang.fragment;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.activityy.ActivityThongTin;
import com.example.quanlybanhang.adapter.HoaDonAdapterRecycler;
import com.example.quanlybanhang.adapter.MyViewPager2Adapter;
import com.example.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.HoaDon;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentHoaDon extends Fragment implements IClickItemListenerRecycer<HoaDon> {

    RecyclerView recyclerViewHoaDon;
    List<HoaDon> hoaDonList = new ArrayList<>();
    HoaDonAdapterRecycler hoaDonAdapterRecycler;
    MySQLiteHelper database;
    HoaDon selectHoaDon;
    TextView textViewHoaDonFilter;
    TextView textViewBaoCao;
    ImageView imageViewSort;
    SearchView searchView;
    Fragment_ChiTietHoaDon fragment_chiTietHoaDon;

    public FragmentHoaDon() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }
    
    
    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.search_viewHoaDOn);
        textViewBaoCao = view.findViewById(R.id.textBaoCao);
        textViewHoaDonFilter = view.findViewById(R.id.textHoaDonFilter);
        imageViewSort = view.findViewById(R.id.sort_img_hoadon);
        recyclerViewHoaDon = view.findViewById(R.id.recyclerHoaDon);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewHoaDon.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        recyclerViewHoaDon.addItemDecoration(dividerItemDecoration);
        database = new MySQLiteHelper(getContext());
        hoaDonList = database.getListHoaDon();
        hoaDonAdapterRecycler = new HoaDonAdapterRecycler(this.getContext(),hoaDonList,this);
        recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
        registerForContextMenu(recyclerViewHoaDon);
        xuLySearch();
        xuLySort();
    }


    private void xuLySort() {
        imageViewSort.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
                    imageViewSort.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(hoaDonList, new Comparator<HoaDon>() {
                        @Override
                        public int compare(HoaDon hoaDon, HoaDon hoaDon1) {
                            if((hoaDon.getTriGia() - hoaDon1.getTriGia())!=0)
                                return (hoaDon.getTriGia() - hoaDon1.getTriGia())>0 ? 1 : -1;
                            else return  hoaDon.getSoHD().compareTo(hoaDon1.getSoHD());
                        }
                    });
                    i=2;
                }
                else if(i==2){
                    imageViewSort.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(hoaDonList, new Comparator<HoaDon>() {
                        @Override
                        public int compare(HoaDon hoaDon, HoaDon hoaDon1) {
                            if((hoaDon.getTriGia() - hoaDon1.getTriGia())!=0)
                                return (hoaDon1.getTriGia() - hoaDon.getTriGia())>0 ? 1 : -1;
                            else return  hoaDon1.getSoHD().compareTo(hoaDon.getSoHD());
                        }
                    });
                    i=3;
                }
                else if(i==3){
                    imageViewSort.setImageResource(R.drawable.ic_baseline_sort_24);
                    hoaDonList.clear();
                    hoaDonList.addAll(database.getListHoaDon());
                    i=1;
                }
                hoaDonAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

    private void xuLySearch() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view == searchView) {
                    if (b) {
                        // Open keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                showSoftInput(searchView, InputMethodManager.SHOW_FORCED);
                    } else {
                        // Close keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    }
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hoaDonAdapterRecycler.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hoaDonAdapterRecycler.getFilter().filter(newText);
                return false;
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        hoaDonList.clear();
        hoaDonList.addAll(database.getListHoaDon());
        imageViewSort.setImageResource(R.drawable.ic_baseline_sort_24);
        searchView.setIconified(true);
        hoaDonAdapterRecycler.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onCreateContextMenu( ContextMenu menu, View v,  ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
        MenuItem menuItemXoa = menu.findItem(R.id.menu_model_xoa);
        MenuItem menuItemSua = menu.findItem(R.id.menu_model_sua);
        menuItemXoa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_xoa);
                builder.setMessage("Bạn có chắc không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.deleteHoaDon(selectHoaDon);
                        hoaDonList.remove(selectHoaDon);
                        hoaDonAdapterRecycler.notifyDataSetChanged();
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
                return false;
            }
        });
        menuItemSua.setVisible(false);
    }

    @Override
    public void onClickItemModel(HoaDon hoaDon) {
        selectHoaDon = hoaDon;
    }

    @Override
    public void onClickChiTietModel(HoaDon hoaDon) {
        Intent intent = new Intent(getContext(), ActivityThongTin.class);
        intent.putExtra("Data",FragmentXemThem.ACT_CHITIETHOADON);
        fragment_chiTietHoaDon = new Fragment_ChiTietHoaDon(hoaDon);
        Bundle bundle = new Bundle();
        bundle.putSerializable("BundleHoaDon",hoaDon);
        intent.putExtra("Fragment",bundle);
        getActivity().startActivity(intent);
    }




}