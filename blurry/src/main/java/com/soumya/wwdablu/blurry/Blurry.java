package com.soumya.wwdablu.blurry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.TextView;

public class Blurry {

    private Context context;

    private Blurry(Context context) {
        this.context = context;
    }

    public static Blurry with(@NonNull Context context) {
        return new Blurry(context);
    }

    public BitmapBlur load(@NonNull Bitmap bitmap) {
        return new BitmapBlur(bitmap);
    }

    public BitmapBlur load(@DrawableRes int resId) {
        return new BitmapBlur(BitmapFactory.decodeResource(context.getResources(), resId));
    }

    public TextViewBlur load(@NonNull TextView textView) {
        return new TextViewBlur(textView);
    }
}
