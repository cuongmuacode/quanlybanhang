package com.example.quanlybanhang.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.fragment.Fragment_San_Pham;
import com.example.quanlybanhang.model.SanPham;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.List;
import java.util.zip.Inflater;

public class SanPhamAdapterRecycler extends RecyclerView.Adapter<SanPhamAdapterRecycler.SanPhamViewHoler> {

    IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer;
    List<SanPham> sanPhamList;

    public SanPhamAdapterRecycler(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
        notifyDataSetChanged();
    }

    public SanPhamAdapterRecycler(IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer, List<SanPham> sanPhamList) {
        this.iClickItemListenerRecycer = iClickItemListenerRecycer;
        this.sanPhamList = sanPhamList;
    }


    @Override
    public SanPhamViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_recyc_view_product,parent,false);
        return new SanPhamViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  SanPhamAdapterRecycler.SanPhamViewHoler holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(iClickItemListenerRecycer!=null){
            if(sanPham == null) return;
            holder.textNameProduct.setText(sanPham.getTenSP());
            holder.textGiaProduct.setText("$"+sanPham.getGiaSP());
            holder.textViewSoLuong.setText("Số lượng : "+sanPham.getSoLuongSP());
            holder.imgProduct.setImageResource(R.drawable.ic_baseline_shopping_bag_24);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemListenerRecycer.onClickChiTietModel(sanPham);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    iClickItemListenerRecycer.onClickItemModel(sanPham);
                    return false;
                }
            });
        }else{
            if(sanPham == null) return;
            holder.textNameProduct.setText(sanPham.getTenSP());
            holder.textGiaProduct.setText("$"+sanPham.getGiaSP()+"x"+sanPham.getSoLuongSP());
            holder.textViewSoLuong.setText("Phải trả : $"+sanPham.getSoLuongSP()*sanPham.getGiaSP());
            holder.imgProduct.setImageResource(R.drawable.ic_baseline_shopping_bag_24);
            holder.textViewRemove.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(sanPhamList!=null) return sanPhamList.size();
        return 0;
    }

    public class SanPhamViewHoler extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView textNameProduct;
        private TextView textGiaProduct;
        private TextView textViewSoLuong;
        private TextView textViewRemove;

        public SanPhamViewHoler(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textNameProduct = itemView.findViewById(R.id.productName);
            textGiaProduct = itemView.findViewById(R.id.productGia);
            textViewSoLuong = itemView.findViewById(R.id.productSoLuong);
            textViewRemove = itemView.findViewById(R.id.textItemRecycProductRemove);
        }
    }
}
