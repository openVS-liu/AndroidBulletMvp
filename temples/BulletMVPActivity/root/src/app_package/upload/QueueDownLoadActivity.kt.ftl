package ${packageName};


import com.bullet.mvp.MvpActivity;
import com.bullet.mvp.ViewInit;
import android.os.Bundle;
<#if userKotLinExtensions??>
import kotlinx.android.synthetic.main.activity_${layout}.*;
</#if>

<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>
<#if isShowTitleBar> 
@ViewInit(layoutName =  "activity_${layout}", title = "${title}")
<#else>
@ViewInit(layoutName =  "activity_${layout}", showTitleBar=false")
</#if>
class ${moudle}Activity : MvpActivity<${moudle}Presenter?>() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState);

    }
}
