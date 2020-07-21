package  com.bullet.mvp;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


import com.bullet.request.Container;
import com.bullet.request.IRequest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @auther lw
 * @date 2019-07-10
 * @des:  Presenter基类
 */
@Keep
public  class Presenter<V extends IView> implements Container, LifecycleObserver {
    protected V view;

    public Presenter() {

    }
    @Override
    public Context getContext() {
       return view==null?null:view.getContext().getApplicationContext();
    }




    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(@NonNull LifecycleOwner owner){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(@NonNull LifecycleOwner owner){
        if (view==null)
        view= (V) owner;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(@NonNull LifecycleOwner owner){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(@NonNull LifecycleOwner owner){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(@NonNull LifecycleOwner owner){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(@NonNull LifecycleOwner owner){

        owner.getLifecycle().removeObserver(this);
    }


    @Override
    public void onBreachAgreement(int code, @Nullable String msg, @Nullable IRequest client) {
        view.onBreachAgreement(code,msg,client);
    }

    @Override
    public void onError(@Nullable String msg, @NotNull IRequest client) {
        view.onError(msg,client);

    }

    @Override
    public void onFinish() {
      view.onFinish();
    }

    @NotNull
    @Override
    public Lifecycle getLifecycle() {
        return view.getLifecycle();
    }

    @Override
    public void showLoadingView(@Nullable String text) {
        view.showLoadingView(text);
    }

    @Override
    public void closeLoadingView() {
      view.closeLoadingView();
    }


}
