package com.example.quanlybanhang.adapter;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.fragment.FragmentAddHoaDon;
import com.example.quanlybanhang.fragment.FragmentBanHang;
import com.example.quanlybanhang.fragment.FragmentHoaDon;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.List;

public class MatHangAdapterRecycler extends RecyclerView.Adapter<MatHangAdapterRecycler.ProductViewHoler>{

    IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer;
    List<SanPham> sanPhamList;

    public MatHangAdapterRecycler(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    public MatHangAdapterRecycler(IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer, List<SanPham> sanPhamList ) {
        this.iClickItemListenerRecycer = iClickItemListenerRecycer;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public ProductViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyc_view_product,parent,false);
        return new ProductViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MatHangAdapterRecycler.ProductViewHoler holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(sanPham == null) return;
        holder.textNameProduct.setText(sanPham.getTenSP());
        holder.textSoLuong.setText("Số  lượng : "+sanPham.getSoLuongSP());
        holder.textGiaProduct.setText("Giá : $"+sanPham.getGiaSP());
        holder.imgProduct.setImageResource(R.drawable.ic_baseline_shopping_bag_24);
        if(iClickItemListenerRecycer.getClass().getName().equals(FragmentAddHoaDon.class.getName())) {
            holder.textRemove.setBackgroundResource(R.drawable.ic_baseline_close_24);
            holder.textRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemListenerRecycer.onClickItemModel(sanPham);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    iClickItemListenerRecycer.onClickChiTietModel(sanPham);
                    return false;
                }
            });
        }

        if(iClickItemListenerRecycer.getClass().getName().equals(FragmentBanHang.class.getName())) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemListenerRecycer.onClickItemModel(sanPham);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(sanPhamList != null)
            return sanPhamList.size();
        return 0;
    }


    public class ProductViewHoler extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView textNameProduct;
        private TextView textSoLuong;
        private TextView textGiaProduct;
        private TextView textRemove;

        public ProductViewHoler(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textNameProduct = itemView.findViewById(R.id.productName);
            textGiaProduct = itemView.findViewById(R.id.productGia);
            textSoLuong = itemView.findViewById(R.id.productSoLuong);
            textRemove = itemView.findViewById(R.id.textItemRecycProductRemove);
        }
    }
}
