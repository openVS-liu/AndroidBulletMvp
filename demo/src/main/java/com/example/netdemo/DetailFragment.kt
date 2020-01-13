package com.example.netdemo

import android.os.Bundle
import android.view.View
import com.bullet.mvp.BaseFragment
import com.bullet.mvp.ViewInit
@ViewInit(layout = R.layout.fragment_detail,showTitleBar = false)
class DetailFragment: BaseFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}