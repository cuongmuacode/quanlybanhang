package com.example.quanlybanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IAddEditModel;
import com.google.android.material.textfield.TextInputEditText;


public class Fragment_Add_SanPham extends Fragment {
    TextInputEditText textInputEditTextTenSP;
    TextInputEditText textInputEditTextDonViTinh;
    TextInputEditText textInputEditTextNuocSX;
    TextInputEditText textInputEditTextChiTietSP;
    TextInputEditText textInputEditTextGiaSP;
    TextInputEditText textInputEditTextSoLuong;
    AppCompatButton appCompatButton;


    IAddEditModel iAddEditModel;
    int addEdit;
    public Fragment_Add_SanPham(IAddEditModel iAddEditModel, int i) {
        this.iAddEditModel = iAddEditModel;
        this.addEdit = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__add__san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputEditTextTenSP = view.findViewById(R.id.inputedittext_tenSanPham);
        textInputEditTextDonViTinh = view.findViewById(R.id.inputedittext_donvitinh);
        textInputEditTextNuocSX = view.findViewById(R.id.inputedittext_NuocSX);
        textInputEditTextChiTietSP = view.findViewById(R.id.inputedittext_ChiTietSanPham);
        textInputEditTextGiaSP  = view.findViewById(R.id.inputedittext_Gia);
        textInputEditTextSoLuong = view.findViewById(R.id.inputedittext_SoLuong);
        appCompatButton = view.findViewById(R.id.buttonaddSanPham);
        Log.i("arr","hi5");
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textInputEditTextChiTietSP.getText().toString().isEmpty()||
                        textInputEditTextDonViTinh.getText().toString().isEmpty()||
                        textInputEditTextGiaSP.getText().toString().isEmpty()||
                        textInputEditTextNuocSX.getText().toString().isEmpty()||
                        textInputEditTextSoLuong.getText().toString().isEmpty()||
                        textInputEditTextTenSP.getText().toString().isEmpty())
                    Toast.makeText(getContext(),"Không được để trống !!!",Toast.LENGTH_SHORT).show();
                else{
                    Log.i("arr","hi8");
                    SanPham sanPham = new SanPham(
                        "MaSP",
                            textInputEditTextTenSP.getText().toString(),
                            textInputEditTextDonViTinh.getText().toString(),
                            textInputEditTextNuocSX.getText().toString(),
                            textInputEditTextChiTietSP.getText().toString(),
                            Long.parseLong(textInputEditTextGiaSP.getText().toString()),
                            Integer.parseInt(textInputEditTextSoLuong.getText().toString())
                    );
                    if(iAddEditModel.processModel(sanPham,addEdit)) getActivity().onBackPressed();
                    else Toast.makeText(getContext(),"Không gửi được thử lại xem ?",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}