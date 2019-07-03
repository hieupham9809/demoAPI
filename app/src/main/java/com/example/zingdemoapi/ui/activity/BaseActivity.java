package com.example.zingdemoapi.ui.activity;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class BaseActivity extends AppCompatActivity {
    protected final CompositeDisposable subscriptions = new CompositeDisposable();

    public void subscribe(Observable observable, Consumer consumer){
        Disposable disposable = observable
                .subscribe(consumer);
        subscriptions.add(disposable);
    }

    public void unsubscribe(){
        subscriptions.clear();
    }
}
