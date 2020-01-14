# AndroidBulletMvp
AndroidBulletMvp是一个轻量级、高效开发、高效运行的Android开发框架，可以最大限度解放开发者、是开发者把全部精力都集中在业务开发上。而不用编写那些重复性、功能逻辑相同的代码。比如每个页面都有一个相同的titleBar。每一次网络请求都需要处理成功、失败，以及数据和预定义的不一致问题。这些问题在AndroidBulletMvp都得到了完美的解决方案。同时AndroidBulletMvp还可以通过模板的方式快速建立一个轻量级MVP架构单元（包括View、Presenter、layout布局文件）。
## 快速上手指南
### 应用模板快速创建文件
#### 1.添加远程依赖库  
      在module添加AndroidBulletMvp的远程依赖库 implementation 'androidx.appcompat:appcompat:1.1.0' 
#### 2. 添加模板   
  下载项目根目录下temples文件夹中的两个模板文件夹（mvpActiviy、mvpFrament）并保存到Android Studio模块文件夹下。
  如果是Mac操作系统则默认为/Applications/Android Studio.app/Contents/plugins/android/lib/templates, Windows系统的话由于差异比较大，就  默认为空了，可以自行配置[Android Studio安装目录]/plugins/android/lib/templates（这里只需要配置一次即可，插件将自动保存该位置）。
####  3.使用模板创建View 、Presenter以及layout布局文件。
   右击任意package目录- New - Activity -mvpAcitiviy。  
  ![image](https://github.com/openVS-liu/AndroidBulletMvp/blob/master/images/2020-01-111.png)
  填写模块信息  
  ![image](https://github.com/openVS-liu/AndroidBulletMvp/blob/master/images/2020-01-13.png)  
  在之前右击的package生成View 、Presenter类文件以及在layout文件夹中生成关联的布局文件。     
  
HomeActivity通过泛型关联了HomePresenter，并且通过ViewInit注解完成了页面的初始化工作，当前页面显示的layout布局文件R.layout.activity_home，并且展示标题为"城市列表"的titleBar。HomeActivity中可以通过present对象调用HomePresenter中封装的方法。presenter对象在HomeActivity中的父类MvpActivity中已经初始化，无需再次初始化。
  ```
  @ViewInit(layout = R.layout.activity_home, title = "城市列表")
 class HomeActivity : MvpActivity<HomePresenter?>() {
    var adapter = CityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }
  }
  
  ```
  HomePresenter通过泛型关联了HomeActivity，并且可以通过view对象直接调用HomeActivity所封装的方法。view对象在HomePresenter的父类Presenter中已经初始化，无需在HomePresenter再次初始化。
  
  ```
  class HomePresenter : Presenter<HomeActivity>() {

    override fun onCreate(@NonNull owner: LifecycleOwner) {
        super.onCreate(owner)

    }
 }
 ```
 activity_home.xml布局文件是一个空的constraintlayout布局。
 ```
  <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="16dp"
    tools:context=".home.HomeActivity">

</androidx.constraintlayout.widget.ConstraintLayout>
 ```
 编译并部署到设备效果如下图  
 
![image](https://github.com/openVS-liu/AndroidBulletMvp/blob/master/images/result11.png)   
### 定制自己项目的网络请求模块 
AndroidBulletMvp的网络模块基于okhttp3.X.X,通过kotlin的协程技术进行二次封装。目标是通过协程技术减少线程的开销从而提高性能。
通过减少重复代码从而提升开发效率（网络链接失败、服务器异常、接口返回数据不符合规范等错误处理都在框架中封装处理，业务层代码不需要关心。如果遇到特效需求也可以简便的重写异常处理。）
#### 使用效果展示
```
 fun requestCityList() {
            StructRequestClient.with(this) //this是Container接口，提供了请求的错误处理、以及请求的生命周期管理等方法。   
                                           // AndroidBulletMvp中 fragment、activity,presenter都实现了Container接口，  
                                          // 所以在什么地方发起网络请求 ，都可以方便的使用"this"                                      
            
            .setUrl("/apis/CityCode/")
            .addParameter("code", "13")
            .setTargetObjectClass(City::class.java) // 规定了请求到的json数据的指定节点要解析成什么对象，程序会根据json数据是对象或者是                                                         //array自动判断需要解析成单个对象或者解析成List
            .setOnSuccessListener { targetObject, _ ->
                view.displayCity(targetObject as List<City>)    //targetObject是解析结果，直接用于业务处理即可。
            }
            .sendRequest()
  }
```
#### StructRequestClient简析、了解如何根据自己的业务封装自己的"StructRequestClient简析"
```
/**
 * 自定义RequestClient的demo
 */
class StructRequestClient : RequestClient() {   //继承自RequestClient类
    var container: Container? = null
    var showLoadingView = true                 //请求的过程中是否需要显示加载页面，默认显示。

    /**
     * 结合自己的业务实际需求，构建Request对象。构建Request对象的构建方法请参照okHttp文档
     * 这部分是需要结合自己业务订制，本方法以一个GET请求为例构造了Request对象。
     */
    override fun createRequest(): Request {   
        val sb = StringBuilder()
        if (!url.startsWith("http")) {
            sb.append(serverUrl)
        }
        sb.append(url)
        var buider = Request.Builder()
        if (parameters != null && parameters.size > 0) {
            var index = 0
            for ((key, value) in parameters) {
                sb.append(if (index == 0) '?' else "&").append(key).append('=').append(value)
                index++
            }
        }
        buider.url(sb.toString())
        if (headers != null && headers.size > 0) {
            for ((key, value) in headers) {
                buider.addHeader(key, value)
            }
        }
        return buider.build();
    }

    /**
     * 返回数据解析器，根据自己业务的数据格式定义
     * StructParser()是一个数据解析的示例类 适用于接口数据格式类似于{"code":0,"data":{},"msg":"错误原因"}或者"code":0,"data":    
     *  [],"msg":"错误原因"}的格式。如果你的项目也是采用类似的格式，可以稍加改动后使用
     */
    override fun getParser(): Parser {
        return StructParser()
    }
    /**
     * 返回接口请求时所用的服务器域名
     */
    override fun getServerUrl(): String {
        return "http://api.help.bj.cn"
    }

    /**
     * 自定义发送http请求
     * 在super.sendRequest()发送请求之前、判断要不要显示加载页面
     */
    override fun sendRequest() {
        if (showLoadingView) {
            container?.showLoadingView("")
        }
        super.sendRequest()
    }

    companion object {  //通过伴生对象的类方法构造StructRequestClient实例，复用Container接口的处理代码。
        /**
         * 简化StructRequestClient对象的配置，Container对象封装了通用的设置。
         */
        fun with(container: Container): StructRequestClient {
            val client = StructRequestClient()
            client.container = container
            with(client) {
                setOnBreachAgreementListenr(container)  //数据不符合预定义错误处理
                setOnFailListener(container)//请求出错处理
                if (showLoadingView) {
                    setOnFinishListener(object : OnFinishListener { //请求完成处理
                        override fun onFinish() {
                            container.closeLoadingView()
                            container.onFinish()
                        }
                    })
                } else {
                    setOnFinishListener(container)
                }
                setLifecycle(container.getLifecycle()) //绑定 activity或者fragment的生命周期
                setContext(container.getContext())
            }
            return client
        }
    }
}
```

## 常用API
ViewInit注解提供了如下方法
```
public @interface ViewInit {
    int layout() default 0;  //页面布局文件的layoutId

    String title() default ""; // titleBar 需要显示的标题

    boolean showBackButton() default true; // 是否显示返回按钮

    boolean showTitleBar() default true; // 是否显示titleBar

    boolean contentViewBlowTitleBar() default true; // layout视图是否需要显示在titleBar下方，

    int titleLayoutId() default 0; // 使用自定义的titilBar布局文件，必须是一个Relative Layout


}
```
IView接口提供的方法(Activity.Fragemt都实现了此接口)

 方法名称  | 参数  | 释意
 ---- | ----- | ------  
 addView(view: View?) | view：需要添加到当前页面的View |  如果当前页面有titleBar，则view填满titleBar以下部分，否则全屏。  
 addView(layoutId: Int) | layoutId：需要添加到当前页面的layout  |  如果当前页面有titleBar，则view填满titleBar以下部分，否则全屏。  
 setTitle(view: View?)  | view  |  把view添加到titleBar中间，当做标题。  
 setTitle(title: String?) | title  |  把title添加到titleBar中间，当做标题。  
addRightButton(view: View?) | view | 把view添加到titleBar的右上方，可以添加多个。  
showLoadingView(text: String?) | text  | 显示加载框，并显示文案text  
cloaseLoadingView() | 无 | 关闭加载框  



    


