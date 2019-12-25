package com.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.batman.baselibrary.R;

public class BottomSheetUtil {

    public TextView tvTitle;
    public RecyclerView rv;

    private Context mContext;

    public BottomSheetUtil(Context context){
        this.mContext = context;
    }

    public BottomSheetDialog showBottomSheet() {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(mContext);
        View cancelView = LayoutInflater.from(mContext).inflate(R.layout.view_custom_bottom_sheet, null);
        ImageView imgCancel = (ImageView) cancelView.findViewById(R.id.img_cancel);
        tvTitle = (TextView) cancelView.findViewById(R.id.tv_title);
        rv = (RecyclerView) cancelView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.setContentView(cancelView);
        View view1 = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        view1.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view1);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return mBottomSheetDialog;
    }


}
