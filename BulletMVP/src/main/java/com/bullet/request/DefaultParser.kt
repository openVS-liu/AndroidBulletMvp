package  com.bullet.request;

import androidx.annotation.Keep

@Keep
class DefaultParser: Parser() {
    override fun parseData(data: String?) {
        if (data.isNullOrEmpty()){
            callBack.breachAgreement(-1,"请求到的数据为空字符串")
        }else{
            callBack.success(data,null)
        }
    }
}