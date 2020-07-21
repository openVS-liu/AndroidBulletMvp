package com.bullet.mvp


import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

open class BaseActivity : AppCompatActivity(), IView {
    override var viewInit: ViewInit? = null
    override var rootView: ViewGroup? = null
    override var titleBar: RelativeLayout? = null
    override var rightButtonGroup: LinearLayout? = null
    override var defaultPadding: Int = 0
    override var contentLayoutParams: FrameLayout.LayoutParams?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewInit = javaClass.getAnnotation(ViewInit::class.java)
        if (viewInit != null) {
            rootView= window.decorView.findViewById<View>(android.R.id.content) as FrameLayout
            initFromAnnotation(viewInit,rootView)
        }
    }
    override fun back(view: View?) {
        finish()
    }


    override fun getContext(): Context {
        return this
    }

}