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



## 轻量级 
  整个框架引用了如下类库外，作者多写代码不足千行。

``` implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2"
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2'
    api 'com.squareup.okhttp3:okhttp:3.12.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0' ```
    


