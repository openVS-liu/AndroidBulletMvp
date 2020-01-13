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
    int layout() default 0;

    String title() default "";

    boolean showBackButton() default true;

    boolean showTitleBar() default true;

    boolean contentViewBlowTitleBar() default true;

    int titleLayouId() default 0;


}
