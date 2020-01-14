# AndroidBulletMvp
AndroidBulletMvp是一个轻量级、高效开发、高效运行的Android开发框架，可以最大限度解放开发者、是开发者把全部精力都集中在业务开发上。而不用编写那些重复性、功能逻辑相同的代码。比如每个页面都有一个相同的titleBar。每一次网络请求都需要处理成功、失败，以及数据和预定义的不一致问题。这些问题在AndroidBulletMvp都得到了完美的解决方案。同时AndroidBulletMvp还可以通过模板的方式快速建立一个轻量级MVP架构单元（包括View、Presenter、layout布局文件）。
## 快速上手指南
1. 在module添加AndroidBulletMvp的远程依赖库 implementation 'androidx.appcompat:appcompat:1.1.0' 
2.添加模板 下载项目根目录下temples文件夹中的两个模板文件夹（mvpActiviy、mvpFrament）并保存到Android Studio模块文件夹下。
  如果是Mac操作系统则默认为/Applications/Android Studio.app/Contents/plugins/android/lib/templates, Windows系统的话由于差异比较大，就  默认为空了，可以自行配置[Android Studio安装目录]/plugins/android/lib/templates（这里只需要配置一次即可，插件将自动保存该位置）。
3.使用模板创建View 、Presenter以及layout布局文件。
   右击任意package目录- New - Activity -mvpAcitiviy。
  ![image](https://github.com/openVS-liu/AndroidBulletMvp/blob/master/images/2020-01-111.png)
  填写模块信息
  ![image](https://github.com/openVS-liu/AndroidBulletMvp/blob/master/images/2020-01-13.png)
  在之前右击的package生成View 、Presenter类文件以及在layout文件夹中生成关联的布局文件。


## 轻量级 
  整个框架引用了如下类库外，作者多写代码不足千行。

``` implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2"
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2'
    api 'com.squareup.okhttp3:okhttp:3.12.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0' ```
    


