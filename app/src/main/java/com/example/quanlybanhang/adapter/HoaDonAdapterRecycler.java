package com.example.quanlybanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlybanhang.R;
import com.example.quanlybanhang.database.MySQLiteHelper;
import com.example.quanlybanhang.model.HoaDon;
import com.example.quanlybanhang.model.KhachHang;
import com.example.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonAdapterRecycler extends RecyclerView.Adapter<HoaDonAdapterRecycler.HoaDonViewHoler> implements Filterable {


    Context context;
    IClickItemListenerRecycer<HoaDon> hoaDonIClickItemListenerRecycer;
    List<HoaDon> hoaDonList;
    List<HoaDon> filterHoaDonList;

    public HoaDonAdapterRecycler(Context context,List<HoaDon> hoaDonList,
                                 IClickItemListenerRecycer<HoaDon> hoaDonIClickItemListenerRecycer) {
        this.context = context;
        this.hoaDonList = hoaDonList;
        filterHoaDonList = hoaDonList;
        this.hoaDonIClickItemListenerRecycer = hoaDonIClickItemListenerRecycer;
    }
    @NonNull
    @Override
    public HoaDonViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_view_hoadon,parent,false);
        return new HoaDonViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  HoaDonAdapterRecycler.HoaDonViewHoler holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);
        if(hoaDon == null) return;
        holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
        holder.maHoaDon.setText(hoaDon.getSoHD());
        MySQLiteHelper database = new MySQLiteHelper(context);
        KhachHang khachHang = database.getKhachHang(hoaDon.getMaKH());
        holder.textKhacHang.setText(khachHang.getTenKH());
        holder.textGiaProduct.setText(hoaDon.getTriGia().toString());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.textngayLapHD.setText(dateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoaDonIClickItemListenerRecycer.onClickChiTietModel(hoaDon);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                hoaDonIClickItemListenerRecycer.onClickItemModel(hoaDon);
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        if(hoaDonList != null) return hoaDonList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty())
                    hoaDonList = filterHoaDonList;
                else{
                    List<HoaDon> list = new ArrayList<>();
                    for(HoaDon hoaDon : hoaDonList){
                        if(hoaDon.getTriGia().toString().toLowerCase().contains(strSearch.toLowerCase())
                                || ("HD"+hoaDon.getSoHD()).toLowerCase().contains(strSearch.toLowerCase())
                                || hoaDon.getSoHD().toLowerCase().contains(strSearch.toLowerCase()))
                            list.add(hoaDon);
                    }
                    hoaDonList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = hoaDonList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hoaDonList =(List<HoaDon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class HoaDonViewHoler extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView maHoaDon;
        TextView textKhacHang;
        TextView textGiaProduct;
        TextView textngayLapHD;

        public HoaDonViewHoler( View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imgHoaDon);
            maHoaDon = itemView.findViewById(R.id.idHoaDon);
            textKhacHang = itemView.findViewById(R.id.hoaDonKhachhang);
            textGiaProduct = itemView.findViewById(R.id.productGiahoadon);
            textngayLapHD =  itemView.findViewById(R.id.ngayHoadon);
        }
    }
}
