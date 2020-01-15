package  com.bullet.mvp;


import com.example.net.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 刘伟 on 2018/9/7
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInit {
    int layout() default 0;  //页面布局文件的layoutId

    String title() default ""; // titleBar 需要显示的标题

    boolean showBackButton() default true; // 是否显示返回按钮

    boolean showTitleBar() default true; // 是否显示titleBar

    boolean contentViewBlowTitleBar() default true; // layout视图是否需要显示在titleBar下方，

    int titleLayoutId() default 0; // 使用自定义的titilBar布局文件，必须是一个Relative Layout


}
