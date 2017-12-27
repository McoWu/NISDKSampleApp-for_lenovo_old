# NISDKSampleApp

## Required Permissions
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.VIBRATE" />
    
## Set AppID
    <meta-data 
        android:name="io.naturali.sdkapi.APPID"
        android:value="lenovo" />
        
## Dependencies
    // for grpc
    compile 'io.grpc:grpc-okhttp:1.6.1'
    compile 'io.grpc:grpc-protobuf-nano:1.6.1'
    compile 'io.grpc:grpc-stub:1.6.1'

    // for rxjava
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'io.reactivex.rxjava2:rxjava:2.1.6'

    // for okhttp
    compile 'com.squareup.okhttp3:okhttp:3.8.1'

    // naturali sdk
    compile project(':naturali-2.0.0.12-release')

## NaturaliSDK API
#### 查看小不点辅助功能服务是否启动
    public static boolean checkAccessibilityService(Context context);
#### 初始化SDK
    public static void initialize();
#### 询问接口 
    query(Context context, String text, NaturaliSDK.QueryCallback callback);
#### 执行接口
    doAction(NaturaliSDK.ActionCallback callback);
#### 回调接口
    NaturaliSDK.QueryCallback {
        response 返回true就表示小不点可以执行该询问，返回false就不能。
        onResult(boolean response);
    }

    NaturaliSDK.ActionCallback {
        执行成功
        onSuccess();
        执行失败
        onFail();
        执行被中断
        onCancel();
    }
