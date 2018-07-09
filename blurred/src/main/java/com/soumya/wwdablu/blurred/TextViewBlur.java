package com.soumya.wwdablu.blurred;

import android.graphics.BlurMaskFilter;
import android.widget.TextView;

public final class TextViewBlur {

    public enum BlurMode {
        INNER,
        NORMAL,
        OUTSIDE,
        SOLID
    }

    private TextView view;

    TextViewBlur(final TextView view) {
        this.view = view;
    }

    public void blur(BlurMode blurMode, float radius) {

        //Check if the view is null
        if(view == null) {
            return;
        }

        BlurMaskFilter.Blur blur = BlurMaskFilter.Blur.NORMAL;
        switch (blurMode) {
            case INNER:
                blur = BlurMaskFilter.Blur.INNER;
                break;

            case OUTSIDE:
                blur = BlurMaskFilter.Blur.OUTER;
                break;

            case SOLID:
                blur = BlurMaskFilter.Blur.SOLID;
                break;
        }

        BlurMaskFilter filter = new BlurMaskFilter(radius, blur);
        view.getPaint().setMaskFilter(filter);
    }
}
