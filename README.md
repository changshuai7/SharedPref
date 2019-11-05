# CSSharedPref:网络请求库
## 写在前边

由于 SharedPreference 的性能原因，此库封装了一个使用方法与SharedPreference相同的类 SharedPref

SharedPref 的底层是使用了ContentProvider，维护了一张 sp_default 的数据表，包含 id, key, values 三个字段。


## 一、框架结构

略


## 二、集成方式

### 1.引入。

```
步骤1：在项目根目录的 build.gradle 中添加：

allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}

步骤2. 在项目app的 build.gradle 的 dependencies 中添加：

dependencies {
        implementation 'com.github.changshuai7:CSSharedPref:0.0.1'
}

版本号 一般采用Tag中最新的版本。
```


### 2.初始化。
#### 2.1在清单文件中初始化

```
<provider
    android:name="com.shuai.sharedpref.SharedPreferenceProvider"
    android:authorities="包名.provider.sharedprefer"  //授权URI
    android:exported="true"                    //设置初始化顺序
    android:initOrder="1" />
```
#### 2.1在Application中初始化

```
@Override
public void onCreate() {
    super.onCreate();
    //由于 ContentResolver 的使用需要依赖 Context，所以 Application 创建时需要传入应用Context
    //【注意，必须在此位置初始化】
    SharedPrefConfig.initContext(this);

}

@Override
protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    //【注意，必须在此位置初始化】
    SharedPrefConfig.getConfig()
            .setDebug(BuildConfig.DEBUG)
            .setSharedPreferenceAuthority("包名.provider.sharedprefer");

}
```

## 三、使用

SharedPref 底层实现是维护了一张 sp_default 的数据表，包含 id, key, values 三个字段。
使用的方式如下：
存数据：
```
SharedPref.setString("test", "存入数据库");
```
取数据：
```
SharedPref.getString("test"));
```

删数据：
```
SharedPref.removeKey("test"));
```
判断是否存在（返回boolean）：
```
SharedPref.contains("test"));
```

