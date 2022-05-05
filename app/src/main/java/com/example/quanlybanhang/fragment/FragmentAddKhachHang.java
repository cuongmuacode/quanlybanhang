package com.example.quanlybanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.example.quanlybanhang.R;
import com.example.quanlybanhang.activityy.ActivityThongTin;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.myinterface.IAddEditModel;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FragmentAddKhachHang extends Fragment {
    AppCompatButton buttonAdd;
    TextInputEditText editTextHoTen;
    TextInputEditText editTextSODT;
    TextInputEditText editTextEmail;
    TextInputEditText editTextDiaChi;
    TextInputEditText editTextGhiChu;
    IAddEditModel iAddEditModel;
    int AddEdit;

    public FragmentAddKhachHang(IAddEditModel iAddEditModel, int i) {
        this.iAddEditModel = iAddEditModel;
        AddEdit = i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_khach_hang, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonAdd = view.findViewById(R.id.buttonaddKhachHang);
        editTextHoTen = view.findViewById(R.id.inputedittext_tenKhachHang);
        editTextSODT = view.findViewById(R.id.inputedittext_SoDienThoai);
        editTextDiaChi = view.findViewById(R.id.inputedittext_DiaChi);
        editTextEmail = view.findViewById(R.id.inputedittext_Email);
        editTextGhiChu = view.findViewById(R.id.inputedittext_GhiChu);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextDiaChi.getText().toString().isEmpty()&&editTextEmail.getText().toString().isEmpty()
                        &&editTextHoTen.getText().toString().isEmpty()&&editTextSODT.getText().toString().isEmpty())
                    Toast.makeText(getContext(),"Không được để trống !!!",Toast.LENGTH_SHORT).show();
                else {
                    KhachHang khachHang = new KhachHang(
                            "MAKH",
                            editTextHoTen.getText().toString(),
                            editTextDiaChi.getText().toString(),
                            editTextEmail.getText().toString(),
                            editTextSODT.getText().toString(),
                            editTextGhiChu.getText().toString()
                    );
                    if(iAddEditModel.processModel(khachHang, AddEdit)) getActivity().onBackPressed();
                    else
                        Toast.makeText(getContext(),"Không gửi được thử lại xem ?",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}