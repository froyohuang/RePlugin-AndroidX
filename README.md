# RePlugin-AndroidX
本Repo的目标是维护一个支持AndroidX的[Qihoo360 RePlugin](https://github.com/Qihoo360/RePlugin)框架分支，欢迎测试&提issue&提PR；

+ Q：为什么不在RePlugin官方提交PR？
+ A：首先，Support库和AndroidX是无法混用的，在Replugin官方上同时兼容暂时没有把握，需要的时间也太多，而很多项目迁移AndroidX已经进行或者马上会进行；另外，Replugin已经大半年没有更新，issue也无官方人员回应，PR提交上去何时能接受是个未知数。但是RePlugin已经是一个很成熟稳定的框架，经我测试切换AndroidX后的宿主和插件，在Android 10上运行也没有问题。

+ Q：如果有一天RePlugin官方复活并支持AndroidX了呢？
+ A：那当然是回归官方了。

## 施工中。。。
## 当前版本2.3.3.0
+ 其中2.3.3为当前RePlugin官方版本，第四位0为RePlugin-AndroidX版本号
+ 支持的特性：宿主和插件都使用AndroidX的情况下，能顺利调起插件Activity，经测试满足几乎所有RePlugin已有功能。
### 2.3.3.0版本接入replugin框架的方法
1. 宿主部分，正常进行AndroidX改造，参照当前host-sample引用host-gradle-androidx和host-library-androidx，需要注意的是要在app的build.gradle中添加依赖:  
```implementation 'androidx.legacy:legacy-support-v4:1.0.0’```
2. 插件部分，正常进行AndroidX改造，参照当前plugin-sample引用本repo的plugin-gradle-androidx和plugin-lib-androidx即可
### 修改点
1. 修改replugin-plugin-gradle中ManifestAPI，使其能够在高版本gradle api下成功获取对应manifest文件
2. 修改replugin-plugin-gradle中LoaderActivityInjector，使其能够匹配androidx包下的FragmentAcvitiy和AppCompatActivity，并进行替换
3. 修改replugin-plugin-library中的PluginFragmentActivity和PluginAppCompatActivity继承AndroidX包中的对应Activity
4. 修改replugin-plugin-library中的PluginLocalBroadcastManager中反射获取LocalBroadcastManager时使用AndroidX包中的对应的类名
5. 修改replugin-host-library中各处对LocalBroadcastManager的引用，改为引用AndroidX包中的类；
6. 修改replugin-host-library中PluginLibraryInternalProxy中对Theme的处理
 ```
    private static int getDefaultThemeId() {
        if (HostConfigHelper.ACTIVITY_PIT_USE_APPCOMPAT) {
            try {
                Class clazz = ReflectUtils.getClass("androidx.appcompat.R$style");
                return (int) ReflectUtils.readStaticField(clazz, "Theme_AppCompat");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return android.R.style.Theme_NoTitleBar;
    }
```
7. 修改replugin-host-gradle，除了更新gradle版本，暂时未见需要修改的地方，插件运行log正常
### 已知问题
1. 在升级demo host的targetsdk到28以后，发现一个replugin和android共同留下的坑，宿主启动插件的Activity在8.0手机上会crash：
```
java.lang.RuntimeException: Unable to start activity ComponentInfo{com.qihoo360.replugin.sample.host/com.qihoo360.replugin.sample.host.loader.a.ActivityN1NRNTS0}: java.lang.IllegalStateException: Only fullscreen opaque activities can request orientation
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2817)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2892)
        at android.app.ActivityThread.-wrap11(Unknown Source:0)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1593)
        at android.os.Handler.dispatchMessage(Handler.java:105)
        at android.os.Looper.loop(Looper.java:164)
        at android.app.ActivityThread.main(ActivityThread.java:6541)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:240)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:767)
     Caused by: java.lang.IllegalStateException: Only fullscreen opaque activities can request orientation
        at android.app.Activity.onCreate(Activity.java:986)
        at com.qihoo360.replugin.loader.a.PluginActivity.onCreate(Unknown Source:3)
        at com.qihoo360.replugin.sample.demo1.MainActivity.onCreate(MainActivity.java:76)
        at android.app.Activity.performCreate(Activity.java:6975)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1213)
```
原因可见[此链接](https://zhuanlan.zhihu.com/p/32190223)，该问题在8.1后已经修复，但是考虑到8.0的用户还是很多，需要解决一下。同时发现将host的repluginHostConfig中的useAppCompat设置为false可以避免此问题，设置为false的唯一区别是坑位Activity的theme从@style/Theme.AppCompat变为@android:style/Theme.NoTitleBar，但是这2个theme都是不透明的。具体原因待查。

## 2.3.3.1版本计划（TODO）
1. 完善sampleplugin测试各项功能兼容程度，重点验证LocalBroadcastManager更换后的影响、AppCompat更换后的影响（特别是theme相关支持）
2. 提交库到远程maven仓库方便使用
3. 修复已知2.3.3.0已知问题1
