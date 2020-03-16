package com.hgwxr.photo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleHolder> {

    @NonNull
    @Override
    public SimpleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleHolder holder, int position) {
        if (position == 0) {
            final View itemView = holder.itemView;
            final ViewPager pager = (ViewPager) itemView.findViewById(R.id.item_view_pager);
            final Context context = itemView.getContext();
            pager.setAdapter(new SimplePageAdapter(context));
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = (int) (dip2px(context, 100));
            itemView.setLayoutParams(layoutParams);
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                private int lastPosition = 0;

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    int px250 = dip2px(context, 250);
                    int px100 = dip2px(context, 100);
//                    if (pager.getCurrentItem() == position) {
//                        if (position == 0) {
//                            positionOffset = 0;
//                        } else {
//                            positionOffset = 1;
//                        }
//                    }

                    if (positionOffset == 0) {
                        positionOffset = position;
                    }
                    float h = (px250 - px100) * positionOffset;
                    Log.d("TAG", "onPageScrolled() called with: position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + (positionOffsetPixels) + "]" + h + "   " + pager.getCurrentItem() + "   " + "   " + position);

                    ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                    layoutParams.height = (int) (px100 + h);
                    itemView.setLayoutParams(layoutParams);
                    lastPosition = position;
                }

                @Override
                public void onPageSelected(int position) {
//                    int px250 = dip2px(context, 250);
//                    int px100 = dip2px(context, 100);
//                    int positionOffset = 0;
//                    if (position == 1) {
//                        positionOffset = 1;
//                    }
//                    float h = (px250 - px100) * positionOffset;
//
//                    ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//                    layoutParams.height = (int) (px100 + h);
//                    itemView.setLayoutParams(layoutParams);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return R.layout.item_view_pager;
        }
        return R.layout.item_normal;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class SimpleHolder extends RecyclerView.ViewHolder {

        public SimpleHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
