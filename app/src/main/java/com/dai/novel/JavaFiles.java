package com.dai.novel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;

/**
 * Created by dai on 2017/7/24.
 */

public class JavaFiles {

    private void getSingleGetRequest() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    List<Cookie> cookies = new ArrayList<>();

    private void test(int a, int b) {
        if (a > b) return;
        if (a < b) System.out.println("a = " + a);

        for(Cookie cookie:cookies){
            System.out.println("cookie = " + cookie);
            System.out.println("cookie = " + cookie.domain());
            System.out.println("cookie = " + cookie.name());
        }
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        JavaFiles.okHttpClient = okHttpClient;
    }

    public static OkHttpClient okHttpClient =null ;
}
