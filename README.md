# RePlugin-AndroidX
本Repo的目标是维护一个支持AndroidX的[Qihoo360 RePlugin](https://github.com/Qihoo360/RePlugin)框架分支，欢迎测试&提issue&提PR；

+ Q：为什么不在RePlugin官方提交PR？
+ A：首先，Support库和AndroidX是无法混用的，在Replugin官方上同时兼容暂时没有把握，需要的时间也太多，而很多项目迁移AndroidX已经进行或者马上会进行；另外，Replugin已经大半年没有更新，issue也无官方人员回应，PR提交上去何时能接受是个未知数。但是RePlugin已经是一个很成熟稳定的框架，经我测试切换AndroidX后的宿主和插件，在Android 10上运行也没有问题。

+ Q：如果有一天RePlugin官方复活并支持AndroidX了呢？
+ A：那当然是回归官方了。

## 持续维护中
## 当前版本2.3.3.0
+ 其中2.3.3为当前RePlugin官方版本，第四位0为RePlugin-AndroidX版本号
+ 支持的特性：宿主和插件都使用AndroidX的情况下，能顺利调起插件Activity，经测试满足几乎所有RePlugin已有功能。
### 2.3.3.0版本接入replugin框架的方法
1. 在宿主和插件项目的根目录的build.gradle中添加maven依赖：
```
maven{
        url "https://dl.bintray.com/froyo-github/Replugin-AndroidX"
     }
```
2. 宿主部分，正常进行AndroidX改造，参照当前host-sample引用host-gradle-androidx和host-library-androidx，
```
classpath "io.github.froyohuang:replugin-host-gradle-androidx:2.3.3.0"
```
```
apply plugin: 'replugin-host-gradle-androidx'
implementation 'io.github.froyohuang:replugin-host-lib-androidx:2.3.3.0'
```
需要注意的是要在app的build.gradle中添加依赖:  
```
implementation 'androidx.legacy:legacy-support-v4:1.0.0’
```
3. 插件部分，正常进行AndroidX改造，参照当前plugin-sample引用本repo的plugin-gradle-androidx和plugin-lib-androidx即可
```
classpath "io.github.froyohuang:replugin-plugin-gradle-androidx:2.3.3.0"
```
```
apply plugin: 'replugin-plugin-gradle-androidx'
implementation 'io.github.froyohuang:replugin-plugin-lib-androidx:2.3.3.0'
```
### 修改点&已知问题
[2.3.3.0修改点&已知问题](https://github.com/froyohuang/RePlugin-AndroidX/wiki/2.3.3.0%E4%BF%AE%E6%94%B9%E7%82%B9&%E5%B7%B2%E7%9F%A5%E9%97%AE%E9%A2%98)
            


## 2.3.3.1版本计划（TODO）
1. 完善sampleplugin测试各项功能兼容程度，重点验证LocalBroadcastManager更换后的影响、AppCompat更换后的影响（特别是theme相关支持）
2. 提交库到远程maven仓库方便使用
3. 修复已知2.3.3.0已知问题1
