package  com.bullet.request;

import android.content.Context
import androidx.lifecycle.Lifecycle

interface Container : OnFinishListener, OnBreachAgreementListener, OnFailListener {
    fun getContext():Context
    fun getLifecycle():Lifecycle
    fun showLoadingView(text:String?)
    fun closeLoadingView()
}