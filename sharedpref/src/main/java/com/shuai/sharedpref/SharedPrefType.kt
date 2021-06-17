package com.shuai.sharedpref

enum class SharedPrefType(
        //Uri的path地址
        val uriPath: String,
        //Uri对应的table名字
        val tableName: String,
) {
    DEFAULT("default", "t_default"),

    // 这里额外申明了另一张表，以备以后需要。
    OTHER("other", "t_other");
}