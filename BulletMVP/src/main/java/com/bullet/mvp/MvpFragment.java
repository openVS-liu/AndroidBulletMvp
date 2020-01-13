package  com.bullet.mvp;

import android.content.Context;
import androidx.fragment.app.Fragment;
import java.lang.reflect.ParameterizedType;

/**
 * @auther liuwei
 * @date 2019-07-10
 * @des:
 */
public  class MvpFragment<T extends Presenter> extends BaseFragment {
    T presenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter=initPresenter();
        if (presenter!=null){
            getLifecycle().addObserver(presenter);
        }
    }
    protected T initPresenter(){
        try {
            Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            return tClass.newInstance();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Fragment.InstantiationException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        presenter=null;
    }
}
