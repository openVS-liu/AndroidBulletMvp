package  com.bullet.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * @auther liuwei
 * @date 2019-07-10
 * @des:  业务层所有页面都需要集成此类
 */
public  class MvpActivity<T extends Presenter> extends BaseActivity {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter=initPresenter();
        if (presenter != null) {
            getLifecycle().addObserver(presenter);
        }
    }


    protected T initPresenter(){
        try {
            Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            return tClass.newInstance();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter=null;
    }


}
