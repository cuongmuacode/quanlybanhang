package com.example.quanlybanhang.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.List;

public class KhachHangAdapterRecycler extends RecyclerView.Adapter<KhachHangAdapterRecycler.KhachHangViewHolder>{

    List<KhachHang> khachHangList;
    IClickItemListenerRecycer clickItemListener;

    public KhachHangAdapterRecycler(List<KhachHang> khachHangList, IClickItemListenerRecycer clickItemListener) {
        this.khachHangList = khachHangList;
        this.clickItemListener = clickItemListener;
    }



    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_view_khachhang,parent,false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  KhachHangAdapterRecycler.KhachHangViewHolder holder, int position) {
        KhachHang khachHang = khachHangList.get(position);
        if (khachHang == null) return;
        holder.imageViewKhachHang.setImageResource(R.drawable.ic_baseline_person_outline_24);
        holder.textViewNameKH.setText(khachHang.getTenKH());
        holder.textViewSDTKH.setText(khachHang.getSoDT());
        holder .textViewEmail.setText(khachHang.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemListener.onClickItemModel(khachHang);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickItemListener.onClickItemModel(khachHang);
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemListener.onClickChiTietModel(khachHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(khachHangList != null)
            return khachHangList.size();
        return 0;
    }


    public class KhachHangViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewKhachHang;
        TextView textViewNameKH;
        TextView textViewEmail;
        TextView textViewSDTKH;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
                 imageViewKhachHang = itemView.findViewById(R.id.imgKhachHang);
                textViewNameKH = itemView.findViewById(R.id.recycler_khachhang_Name);
             textViewSDTKH = itemView.findViewById(R.id.recycler_khachhang_SDT);
             textViewEmail = itemView.findViewById(R.id.recycler_khachhang_Email);
        }
    }
}
