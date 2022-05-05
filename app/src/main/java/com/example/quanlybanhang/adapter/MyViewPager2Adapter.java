package com.example.quanlybanhang.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlybanhang.fragment.FragmentBanHang;
import com.example.quanlybanhang.fragment.FragmentBaoCao;
import com.example.quanlybanhang.fragment.FragmentHoaDon;
import com.example.quanlybanhang.fragment.FragmentXemThem;

public class MyViewPager2Adapter extends FragmentStateAdapter {


    public MyViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MyViewPager2Adapter(@NonNull  Fragment fragment) {
        super(fragment);
    }

    public MyViewPager2Adapter(@NonNull  FragmentManager fragmentManager, @NonNull  Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new FragmentBanHang();
            case 1: return new FragmentHoaDon();
            case 2: return new FragmentBaoCao();
            case 3: return new FragmentXemThem();
        }
        return  new FragmentBanHang();
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        return super.containsItem(itemId);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
