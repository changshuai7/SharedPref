package com.shuai.sharedpref;

public enum SharedPrefType {

    DEFAULT("default", "t_default"),
    // 这里额外申明了另一张表，以备以后需要。
    OTHER("other", "t_other");

    public final String uriPath;//Uri的path地址
    public final String tableName;//Uri对应的table名字

    SharedPrefType(String uriPath, String tableName) {
        this.uriPath = uriPath;
        this.tableName = tableName;
    }
}