# AndroidBulletMvp
1. AndroidBulletMvp是一个轻量级、可以在不牺牲性能的前提下、可以大幅提高开发效率的敏捷框架。
2. 通过kotlin的协程技术，节省异步网络并发请求时候的线程消耗，从而提升运行性能。
3. 通过一行注解轻松实现布局文件的加载显示、titbar的自动添加（不需要写在布局文件中）
4. 通过调用addView方法实现在页面（Activity Fragment）覆盖View. 网络异常、空页面、加载页面等页面可以轻松实现动态加载无需预先写在布局文件中
5. 对网络请求进行链式调用封装，以及抽象出相同的功能以及流程从而提高开发效率（业务层代码开发者只需要处理核心业务，而不需要关心每次网络请求的异常处理、以及数据异常处理。如果遇到特殊需求，可以通过添加异常回调单独处理异常情况）
6. 同过添加AS模板文件，实现MVP模型的快速创建。除了每个组件的初始化代码外（Activity：oncreate（）Fragment：onViewCreate（） Presenter：onCreate（）），无需任何样板代码。
7. 轻量级、如果您用 kotlin语言开发app，对app的体积增加不足50K，几乎可以忽略不计。

、
## 快速上手指南
### 应用模板快速创建文件
#### 1.添加远程依赖库

     Add it in your root build.gradle at the end of repositories:

     	allprojects {
     		repositories {
     			...
     			maven { url 'https://jitpack.io' }
     		}
     	}
     Step 2. Add the dependency

     	dependencies {
     	        implementation 'com.github.openVS-liu:AndroidBulletMvp:v1.0.0'
     	}

#### 2. [Android Bullet框架介绍1-一行代码搞定Activity、Fragment的titlebar以及布局的加载](https://editor.csdn.net/md/?articleId=107634119)
#### 3. [Bullet框架介绍2-在 Activity或Fragment中动态添加或者移出View](https://editor.csdn.net/md/?articleId=107634214)
 #### 4. [Android Bulle介绍3-Bulle中的MVP模式](https://editor.csdn.net/md/?articleId=107634720)
 #### 5. [Android Bullet框架介绍4 一使用Android studio模板快速创建View、Presenter以及布局文件](https://editor.csdn.net/md/?articleId=105988275)
 #### 6. [Android Android Bullt 介绍5 -网络请求，敏捷到不剩底裤](https://editor.csdn.net/md/?articleId=105988513)
 #### 7. [Android Bullet介绍6——自定义自己的主题](https://editor.csdn.net/md/?articleId=105988602)
 #### 8. [Android Bullet框架和rxjava+retrofit框架使用对比](https://editor.csdn.net/md/?articleId=107658797)
