package com.projectyr4x00091174.carl.traingain;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by carl on 16/04/2015.
 */
public class MyProgressDialog extends Dialog {

    private ImageView myImageView;

    public MyProgressDialog(Context context, int imgResourceId) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams myLWindow = getWindow().getAttributes();
        myLWindow.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(myLWindow);
        setTitle("Warming Up....");
        setCancelable(true);
        setOnCancelListener(null);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myImageView = new ImageView(context);
        myImageView.setImageResource(imgResourceId);
        linearLayout.addView(myImageView, params);
        addContentView(linearLayout, params);
    }

    @Override
    public void show() {
        super.show();
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(3000);
        myImageView.setAnimation(rotateAnimation);
        myImageView.startAnimation(rotateAnimation);
    }
}