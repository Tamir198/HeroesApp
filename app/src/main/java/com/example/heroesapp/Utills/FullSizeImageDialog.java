package com.example.heroesapp.Utills;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.heroesapp.R;

public class FullSizeImageDialog extends Dialog {
    private ImageView imageView;
    private Context dialogContext;

    public FullSizeImageDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.full_size_image_dialog);
        dialogContext = context;
        imageView = findViewById(R.id.full_size_image);
    }

    public void setImageFullSize(String imageResource){
        Glide.with(dialogContext).load(imageResource).into(imageView);
    }


}
