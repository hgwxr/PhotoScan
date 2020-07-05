package com.hgwxr.photo.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;


import com.hgwxr.wechartfiletools.utils.UiThreadUtil;

import java.util.Map;


public class StatusBarHelper {
    private Activity mActivity;
    private static final String HEIGHT_KEY = "HEIGHT";

    private StatusBarHelper(Activity activity) {
        this.mActivity = activity;
    }

    public static StatusBarHelper create(Activity activity) {
        return new StatusBarHelper(activity);
    }


    public int getStatusBarColor(Activity activity) {
        int curColor = -1;
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    curColor = activity.getWindow().getStatusBarColor();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        return curColor;
    }

    public String getStatusBarStyle(Activity activity) {
        if (activity == null) {
            return "light-content";
        }
        String style = "light-content";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                View decorView = activity.getWindow().getDecorView();
                int systemUiVisibility = decorView.getSystemUiVisibility();
                style = systemUiVisibility == View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR ? "dark-content" : style;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return style;
    }

    public void setDefaultColor() {
        int gray = Color.GRAY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gray = Color.TRANSPARENT;
        }
        setColor(gray, true);
    }

    public void setColor(final int color, final boolean animated) {
        final Activity activity = mActivity;
        if (activity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UiThreadUtil.INSTANCE.runOnUiThread(new Runnable() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    try {
                        if (animated) {
                            int curColor = activity.getWindow().getStatusBarColor();
                            ValueAnimator colorAnimation = ValueAnimator.ofObject(
                                    new ArgbEvaluator(), curColor, color);

                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    activity.getWindow().setStatusBarColor((Integer) animator.getAnimatedValue());
                                }
                            });
                            colorAnimation
                                    .setDuration(300)
                                    .setStartDelay(0);
                            colorAnimation.start();
                        } else {
                            activity.getWindow().setStatusBarColor(color);
                        }
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    public void setTranslucent(final boolean translucent) {
        final Activity activity = mActivity;
        if (activity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UiThreadUtil.INSTANCE.runOnUiThread(new Runnable() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    // If the status bar is translucent hook into the window insets calculations
                    // and consume all the top insets so no padding will be added under the status bar.
                    try {
                        View decorView = activity.getWindow().getDecorView();
                        if (translucent) {
                            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                                @Override
                                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                                    WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                                    return defaultInsets.replaceSystemWindowInsets(
                                            defaultInsets.getSystemWindowInsetLeft(),
                                            0,
                                            defaultInsets.getSystemWindowInsetRight(),
                                            defaultInsets.getSystemWindowInsetBottom());
                                }
                            });
                        } else {
                            decorView.setOnApplyWindowInsetsListener(null);
                        }

                        ViewCompat.requestApplyInsets(decorView);

                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setHidden(final boolean hidden) {
        final Activity activity = mActivity;
        if (activity == null) {
            return;
        }
        UiThreadUtil.INSTANCE.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (hidden) {
                                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                            } else {
                                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            }
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void setStyle(final String style) {
        final Activity activity = mActivity;
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            UiThreadUtil.INSTANCE.runOnUiThread(
                    new Runnable() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            try {
                                View decorView = activity.getWindow().getDecorView();
                                decorView.setSystemUiVisibility(
                                        style.equals("dark-content") ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0);
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
    }

    public void setBarDarkStyle() {
        setStyle("dark-content");
    }

    public void setBarLightStyle() {
        setStyle("light-content");
    }
}
