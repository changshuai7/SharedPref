# SharedPref:高效SP存储库
## 写在前边

由于 SharedPreference 的性能原因，此库封装了一个使用方法与SharedPreference相同的类 SharedPref

SharedPref 的底层是使用了ContentProvider。有如下优点：

1. 性能高效
2. 支持多线程访问、多进程访问，且线程进程安全。
2. 支持跨应用访问，并支持配置是否允许外部访问的权限。


## 一、框架结构

略


## 二、集成方式

### 1.引入。

```
dependencies {
        implementation 'com.shuai:sharedpref:0.0.3'
}

版本号 一般采用Tag中最新的版本。
```


### 2.初始化。
在Application中初始化

```
@Override
public void onCreate() {
    super.onCreate();
     /**
     * 请在Application中完成初始化操作
     *
     * @param context             上下文
     * @param debug               是否是debug模式
     * @param allowExternalVisit  是否允许外部应用程序访问数据
     */
    SharedPref.init(this,BuildConfig.DEBUG,true);
}
```

## 三、使用


存数据：
```
SharedPref.setString("test", "存入数据库");
```
取数据：
```
SharedPref.getString("test");
```

判断是否存在（返回boolean）：
```
SharedPref.contains("test");
```
删数据：
```
SharedPref.removeKey("test");
```
删除全部数据：
```
SharedPref.clearAll();
```

如果需要跨应用访问，则所有API第一个参数加入要访问的包名即可，比如：

```
//存数据
SharedPref.setString(“com.baidu.com”, "test", "存入数据库");
```
