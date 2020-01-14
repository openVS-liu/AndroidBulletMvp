# AndroidBulletMvp
AndroidBulletMvp是一个轻量级、高效开发、高效运行的Android开发框架，可以最大限度解放开发者、是开发者把全部精力都集中在业务开发上。而不用编写那些重复性、功能逻辑相同的代码。比如每个页面都有一个相同的titleBar。每一次网络请求都需要处理成功、失败，以及数据和预定义的不一致问题。这些问题在AndroidBulletMvp都得到了完美的解决方案。同时AndroidBulletMvp还可以通过模板的方式快速建立一个轻量级MVP架构单元（包括View、Presenter、layout布局文件）。

## 轻量级 
  整个框架引用了如下类库外，作者多写代码不足千行。

``` implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2"
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2'
    api 'com.squareup.okhttp3:okhttp:3.12.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0' ```

