# SharedPref:高效SP存储库
## 写在前边

由于 SharedPreference 的性能原因，此库封装了一个使用方法与SharedPreference相同的类 SharedPref

SharedPref 的底层是使用了ContentProvider，维护了一张 sp_default 的数据表，包含 id, key, values 三个字段。


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
    //初始化
    SharedPref.init(this);
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

删数据：
```
SharedPref.removeKey("test");
```
判断是否存在（返回boolean）：
```
SharedPref.contains("test");
```

