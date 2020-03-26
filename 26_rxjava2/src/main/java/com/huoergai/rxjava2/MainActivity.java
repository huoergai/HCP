package com.huoergai.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        Single.just("test")
                .subscribe(new SingleObserver<String>() {
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

    private void fetchRepos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
}
