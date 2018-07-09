package com.soumya.wwdablu.blurred;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.TextView;

public class Blurred {

    private Context context;

    private Blurred(Context context) {
        this.context = context;
    }

    public static Blurred with(@NonNull Context context) {
        return new Blurred(context);
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
