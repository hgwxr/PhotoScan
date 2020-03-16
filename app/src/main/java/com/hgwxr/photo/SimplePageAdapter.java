package com.hgwxr.photo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimplePageAdapter extends PagerAdapter {
    private List<ImageView> views = new ArrayList<>();

    public SimplePageAdapter(Context context) {
        ImageView e = new ImageView(context);
        ImageView e1 = new ImageView(context);
        views.add(e);
        views.add(e1);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);

    }


    @Override
    public ImageView instantiateItem(ViewGroup container, int position) {
        ImageView imageView = views.get(position);
        Context context = container.getContext();
        if (position==0) {
            imageView.setBackground(context.getDrawable(R.drawable.ic_launcher_background));
        }else{
            imageView.setBackground(context.getDrawable(R.drawable.bg_shape));
        }
        container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip2px(context,position==0?100:250) ));
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView) object;
        container.removeView(imageView);
    }
}
