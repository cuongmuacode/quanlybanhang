package com.example.quanlybanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.SanPham;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentThemSoLuong extends Fragment {
    TextInputEditText textInputEditText;
    AppCompatButton appCompatButton;

    SanPham selectSanPham;
    public FragmentThemSoLuong(SanPham selectSanPham) {
        // Required empty public constructor
        this.selectSanPham = selectSanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_themsoluong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appCompatButton = view.findViewById(R.id.button_add_soluong);
        textInputEditText = view.findViewById(R.id.inputedittext_add_SoLuong);
        MySQLiteHelper database = new MySQLiteHelper(getContext());
        SanPham sanPham = database.getSanPham(selectSanPham.getMaSP());
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sanPham.getSoLuongSP() < Integer.parseInt(textInputEditText.getText().toString()))
                    Toast.makeText(getContext(),
                            "Chỉ còn "+sanPham.getSoLuongSP()+" sản phẩm!!",Toast.LENGTH_SHORT).show();
                else {
                    selectSanPham.setSoLuongSP(
                            Integer.parseInt(textInputEditText.getText().toString()));
                    getActivity().onBackPressed();
                }

            }
        });

    }
}