package com.huoergai.rxjava2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
    }

    public void onClick(View v) {
        // fetchRepos();
        // rxjavaTest1();
        rxjavaTest2();
    }

    private void fetchRepos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        NetApi netApi = retrofit.create(NetApi.class);
        netApi.getRepos("huoergai")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Repo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        tv.setText(R.string.loading);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Repo> repos) {
                        tv.setText(repos.get(2).full_name);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tv.setText(e.getMessage());
                    }
                });
    }

    private void rxjavaTest1() {
        Single<String> stringSingle = Single.just("test")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Throwable {
                        return "mapped+" + s;
                    }
                });
        stringSingle.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(String s) {
                tv.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void rxjavaTest2() {
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        tv.setText(R.string.start);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        tv.setText(String.format(Locale.CHINA, "%ds", aLong));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tv.setText(R.string.error);
                    }

                    @Override
                    public void onComplete() {
                        tv.setText(R.string.complete);
                    }
                });

    }

}
