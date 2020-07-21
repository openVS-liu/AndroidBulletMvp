package  com.bullet.request;

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.Lifecycle
@Keep
interface Container : OnFinishListener, OnBreachAgreementListener, OnFailListener {
    fun getContext():Context
    fun getLifecycle():Lifecycle
    fun showLoadingView(text:String?)
    fun closeLoadingView()
}