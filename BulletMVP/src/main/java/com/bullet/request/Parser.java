package  com.bullet.request;

import androidx.annotation.Keep;

import java.util.HashMap;
@Keep
public abstract class Parser {

    public Class wholeNodeClass;
    public HashMap<String, Class<?>> childrenNodesClass;
    protected ParserCallBack callBack;

    public abstract void parseData(String data);

    public interface ParserCallBack {
        public void success(Object object, HashMap<String, Object> objects);

        public void fail(String info);

        public void breachAgreement(int code, String info);
    }
}
