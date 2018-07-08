package com.soumya.wwdablu.blurry;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BitmapBlur {

    private Bitmap originalBitmap;
    private int fogginess;
    private boolean async = true;

    public BitmapBlur(Bitmap originalBitmap) {
        this.originalBitmap = originalBitmap;
    }

    /**
     * Value ranges from 1 to 25.
     * @param fog Blur factor
     * @return Instance
     */
    public BitmapBlur fogginess(int fog) {

        if(fog < 0 || fog > 25) fog = Constants.DEFAULT_FOGGINESS;

        this.fogginess = fog;
        return this;
    }

    /**
     * Perform the blur action on the calling thread. If not called, then the blur operation
     * would be performed on a worker thread. Once completed, the result will be returned to
     * the main thread. Default, it works in asynchronous mode.
     * @return
     */
    public BitmapBlur synchronous() {
        async = false;
        return this;
    }

    @SuppressLint("CheckResult")
    public void in(@NonNull final ImageView imageView) {

        if(async) {

            Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
                emitter.onNext(blur(imageView));
                emitter.onComplete();
            }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribeWith(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //
                    }

                    @Override
                    public void onComplete() {
                        //
                    }
                });

        } else {
            imageView.setImageBitmap(blur(imageView));
        }
    }

    @Nullable
    private Bitmap blur(ImageView imageView) {

        if(imageView == null) {
            return null;
        }

        Bitmap output = Bitmap.createBitmap(originalBitmap);

        RenderScript rs = RenderScript.create(imageView.getContext());
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element  .U8_4(rs));

        Allocation ipAlloc = Allocation.createFromBitmap(rs, originalBitmap);
        Allocation opAlloc = Allocation.createFromBitmap(rs, output);
        intrinsicBlur.setRadius(fogginess);
        intrinsicBlur.setInput(ipAlloc);
        intrinsicBlur.forEach(opAlloc);

        opAlloc.copyTo(output);

        opAlloc.destroy();
        ipAlloc.destroy();
        intrinsicBlur.destroy();
        rs.finish();
        rs.destroy();

        return output;
    }
}
