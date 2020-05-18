package ${packageName};
import com.bullet.mvp.MvpFragment;
import com.bullet.mvp.ViewInit;
import android.os.Bundle;
import androidx.annotation.Nullable;
<#if applicationPackage??>
import ${applicationPackage}.R;
</#if>
<#if isShowTitleBar>

@ViewInit(layoutName= "fragment_${layout}", title = "${title}")
<#else>
@ViewInit(layoutName= "fragment_${layout}", showTitleBar=false")
</#if>


public class ${moudle}Fragment extends MvpFragment<${moudle}Presenter> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
