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

    /**
     * 构建Request对象
     * @return Request
     */
    protected abstract Request createRequest();

    /**
     * 返回数据解析器
     * @return Parser是一个抽象类，要根据真实业务重写 parseData(String data)方法，
     * 具体方法请参考Demo中的structParser类，如果只需要把数据库请求到的字符串返回给业务层，可以直接
     * 返回DefaultParser对象
     */
    protected abstract Parser getParser();

    /**
     * 返回服务器地址
     * @return
     */
    protected abstract String getServerUrl();

    /**
     * 添加http请求头
     * @param key 请求头名称
     * @param value 请求头名称请求头值
     * @return RequestClient
     */
    public RequestClient addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }

        headers.put(key, value);
        return this;
    }

    /**
     * 添加http请求参数
     * @param key 参数名称
     * @param value 参数值
     * @return RequestClient
     */
    public RequestClient addParameter(String key, String value) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        parameters.put(key, value);
        return this;
    }

    /**
     * 设置需要把数据解析成那个Class的对象。在demo中的SructParser中，会把{"status":0,data:{},"msg":""}或者{"status":0,data:{},"msg":""}
     * 格式的json字符串中data节点解析为targetObjectClass对象或者targetObjectClass类型的List
     * @param targetObjectClass 指定解析类型的的Class
     * @return
     */
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

    /**
     * 设置打印日志的tag，通过此tag可以在logCat中过滤本次网络请求的日志
     * @param tag
     * @return
     */
    public RequestClient setLogTag(String tag) {
        logTag = tag;
        return this;
    }

    public RequestClient setContext(Context context) {
        mContext = context;
        return this;
    }

    /**
     * 设置请求所依附的生命周期
     * @param lifecycle 生命周期
     * @return
     */
    public RequestClient setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);

        return this;
    }

    /**
     * 设置请求的url
     * @param url ，可以是完整的http、https路径。如果在getServerUrl()中返回了非空的服务器地址，url也可以只有path字符串。
     * @return
     */
    public RequestClient setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置请求失败的回调监听
     * @param listener
     * @return
     */
    public RequestClient setOnFailListener(OnFailListener listener) {
        this.onFailListener = listener;
        return this;
    }


    /**
     * 设置请求并解析成功的回调监听
     * @param listener
     * @return
     */
    public RequestClient setOnSuccessListener(OnSuccessListener listener) {
        this.onSuccessListener = listener;
        return this;
    }

    /**
     * 设置请求结束的回调监听，无论请求是否成功都会调用此回调
     * @param listener
     * @return
     */
    public RequestClient setOnFinishListener(OnFinishListener listener) {
        this.onFinishListener = listener;
        return this;
    }

    /**
     * 设置请求到的数据，不符合协议的回调监听。例如{"status":0,data:{},"msg":""}格式的数据，约定了只有status==0时时正常请求，否则都是
     * 异常不符合协议的情况。此时会吧statue值和msg异常详情回调给业务代码。
     * @param listener
     * @return
     */
    public RequestClient setOnBreachAgreementListener(OnBreachAgreementListener listener) {
        this.onBreachAgreementListenr = listener;
        return this;
    }

    /**
     * 设置是否可以打印请求信息以及返回的信息
     * @param logEnable 建议release版本设置为false，debug版本设置为true
     * @return
     */
    public RequestClient setLogEnable(boolean logEnable) {
        this.logEnable = logEnable;
        return this;
    }
    /**
     * 取消本次请求
     * 和设置的Lifecycle的生命周期绑定，当Lifecycle调用onDestroy()时候 cancle()会自动调用
     */
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

    /**
     * 发送请求，通过kotlin的协程技术、调用okhttp的同步请求方法，减少线程的开销从而提高运行性能
     */
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
