package ${packageName};

import com.bullet.mvp.MvpActivity;
import com.bullet.mvp.ViewInit;
import android.os.Bundle;
<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>
<#if isShowTitleBar> 
@ViewInit(layoutName = "activity_${layout}", title = "${title}")
<#else>

@ViewInit(layoutName = "activity_${layout}", showTitleBar=false")
</#if>
public class ${moudle}Activity extends MvpActivity<${moudle}Presenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
