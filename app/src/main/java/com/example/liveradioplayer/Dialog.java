package com.example.liveradioplayer;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Dialog {

    public KProgressHUD progressDialog;

    public void startDialog(Context context){
        progressDialog = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setCancellable(false)
                .setBackgroundColor(context.getResources().getColor(R.color.colorPrimary))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}
