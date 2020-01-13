package  com.bullet.request;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import okhttp3.Request;

public abstract class RequestClient implements IRequest, LifecycleObserver {
    Lifecycle lifecycle;
    String logTag = "";
    private boolean canceled = false;
    protected String url = null;
    Courier courier = new Courier();
    OnFailListener onFailListener = null;
    OnFinishListener onFinishListener = null;
    OnSuccessListener onSuccessListener = null;
    OnBreachAgreementListener onBreachAgreementListenr;
    boolean logEnable = false;
    Class<?> targetObjectClass;
    protected HashMap<String, Class<?>> childrenObjectClass;
    protected Context mContext;

    protected HashMap<String, String> headers;
    protected HashMap<String, String> parameters;

    protected abstract Request createRequest();

    protected abstract Parser getParser();

    protected abstract String getServerUrl();


    public RequestClient addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }

        headers.put(key, value);
        return this;
    }

    public RequestClient addParameter(String key, String value) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        parameters.put(key, value);
        return this;
    }

    public RequestClient setTargetObjectClass(Class<?> targetObjectClass) {
        this.targetObjectClass = targetObjectClass;
        return this;
    }

    public RequestClient addNodeObjectClass(String nodeName, Class<?> targetObjectClass) {
        if (childrenObjectClass == null) {
            childrenObjectClass = new HashMap<>();
        }
        childrenObjectClass.put(nodeName, targetObjectClass);

        return this;
    }

    public RequestClient setLogTag(String tag) {
        logTag = tag;
        return this;
    }

    public RequestClient setContext(Context context) {
        mContext = context;
        return this;
    }

    public RequestClient setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);

        return this;
    }

    public RequestClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestClient setOnFailListener(OnFailListener listener) {
        this.onFailListener = listener;
        return this;
    }


    //
    public RequestClient setOnSuccessListener(OnSuccessListener listener) {
        this.onSuccessListener = listener;
        return this;
    }

    //
    public RequestClient setOnFinishListener(OnFinishListener listener) {
        this.onFinishListener = listener;
        return this;
    }


    public RequestClient setOnBreachAgreementListenr(OnBreachAgreementListener listener) {
        this.onBreachAgreementListenr = listener;
        return this;
    }


    public RequestClient setLogEnable(boolean logEnable) {
        this.logEnable = logEnable;
        return this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void cancel() {
        courier.cancel();
        courier = null;
        onFailListener = null;
        onFinishListener = null;
        onSuccessListener = null;
        onBreachAgreementListenr = null;
        canceled = true;
        lifecycle.removeObserver(this);

    }

    @Override
    public void sendRequest() {

        courier.setLogEnable(logEnable);
        courier.setLogTag(logTag);
        courier.request = createRequest();
        final IRequest iRequest = this;
        courier.setCallback(new Courier.RequestCallBack() {
            @Override
            public void success(@NotNull String result) {
                Parser parser = getParser();
                parser.wholeNodeClass = targetObjectClass;
                parser.childrenNodesClass = childrenObjectClass;
                parser.callBack = new Parser.ParserCallBack() {
                    @Override
                    public void success(final Object object, final HashMap<String, Object> objects) {

                        Courier.Companion.runMainThreadTask(new Runnable() {
                            @Override
                            public void run() {
                                if (onSuccessListener != null)
                                    onSuccessListener.onSuccess(object, objects);
                                if (onFinishListener != null)
                                    onFinishListener.onFinish();
                            }
                        });
                    }

                    @Override
                    public void fail(final String info) {
                        Courier.Companion.runMainThreadTask(new Runnable() {
                            @Override
                            public void run() {
                                if (onFailListener != null)
                                    onFailListener.onError(info, iRequest);
                                if (onFailListener != null)
                                    onFinishListener.onFinish();
                            }
                        });
                    }

                    @Override
                    public void breachAgreement(final int code, final String info) {
                        Courier.Companion.runMainThreadTask(new Runnable() {
                            @Override
                            public void run() {
                                if (onBreachAgreementListenr != null)
                                    onBreachAgreementListenr.onBreachAgreement(code, info, iRequest);
                                if (onFinishListener != null)
                                    onFinishListener.onFinish();
                            }
                        });
                    }
                };
                parser.parseData(result);
            }

            @Override
            public void serverError(final int code) {
                Courier.Companion.runMainThreadTask(new Runnable() {
                    @Override
                    public void run() {
                        if (onFailListener != null)
                            onFailListener.onError("服务器异常，错误码：" + code, iRequest);
                        if (onFailListener != null)
                            onFinishListener.onFinish();
                    }
                });
            }

            @Override
            public void connectionError(@NotNull Exception exception) {
                Courier.Companion.runMainThreadTask(new Runnable() {
                    @Override
                    public void run() {
                        if (onFailListener != null)
                            onFailListener.onError("网络链接失败，请检查网络!：", iRequest);
                        if (onFailListener != null)
                            onFinishListener.onFinish();
                    }
                });
            }
        });
        courier.sendRequest();

    }
}
