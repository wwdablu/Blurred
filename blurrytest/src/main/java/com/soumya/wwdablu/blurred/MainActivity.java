package com.soumya.wwdablu.blurred;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.soumya.wwdablu.blurry.Blurry;
import com.soumya.wwdablu.blurry.TextViewBlur;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textBlur();
        ticker();
    }

    private void imageBlur(long l) {

        ImageView iv = findViewById(R.id.iv_blur_image);
        Blurry.with(getApplicationContext())
            .load(R.drawable.sample)
            .fogginess((int) l)
            .in(iv);
    }

    private void textBlur() {

        TextView tv = findViewById(R.id.tv_sample_text);
        Blurry.with(getApplicationContext())
            .load(tv)
            .blur(TextViewBlur.BlurMode.OUTSIDE, 20);
    }

    @SuppressLint("CheckResult")
    private void ticker() {

        Observable.interval(3, 100, TimeUnit.MILLISECONDS)
                .takeWhile(aLong -> aLong <= 50)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {

                        TextView tv = findViewById(R.id.tv_sample_text);
                        tv.setText(aLong.toString());

                        if(aLong < 25) {
                            imageBlur(aLong);
                        } else {
                            imageBlur(50 - aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
