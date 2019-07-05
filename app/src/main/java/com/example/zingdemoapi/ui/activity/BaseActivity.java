package com.example.zingdemoapi.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DisposableSubscriber;


public class BaseActivity extends AppCompatActivity {
    protected final CompositeDisposable subscriptions = new CompositeDisposable();

    public void subscribe(Observable observable, Consumer next, Consumer error, Action complete){
        Disposable disposable = observable
                .subscribe(next, error, complete);
        //Log.d("ZingDemoApi", observable.getClass().get);

        subscriptions.add(disposable);
    }

    public void unsubscribe(){
        subscriptions.dispose();
    }
}
