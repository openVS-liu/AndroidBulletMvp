package ${packageName};

import com.bullet.mvp.MvpFragment
import com.bullet.mvp.ViewInit
import android.os.Bundle
import android.view.View
<#if applicationPackage??>
import ${applicationPackage}.R
</#if>
<#if userKotLinExtensions??>
import kotlinx.android.synthetic.main.fragment_${layout}.*;
</#if>
<#if isShowTitleBar> 
@ViewInit(layout = R.layout.fragment_${layout}, title = "${title}")
<#else>
@ViewInit(layout = R.layout.fragment_${layout}, showTitleBar=false")
</#if>
class ${moudle}Fragment : MvpFragment <${moudle}Presenter?>() {

    override fun  onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
