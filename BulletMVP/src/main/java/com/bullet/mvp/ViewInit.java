package  com.bullet.mvp;

import androidx.annotation.Keep;

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
@Keep
public @interface ViewInit {
    String layoutName() ;  //页面布局文件的名称，之所以用String而不是int 是因为module作为library无法使用 R.layout.xxxx 变量

    String title() default ""; // titleBar 需要显示的标题

    boolean showBackButton() default true; // 是否显示返回按钮

    boolean showTitleBar() default true; // 是否显示titleBar

    boolean contentViewBlowTitleBar() default true; // layout视图是否需要显示在titleBar下方，

    int titleLayoutId() default 0; // 使用自定义的titilBar布局文件，必须是一个Relative Layout

}
