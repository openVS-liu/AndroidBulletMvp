package com.bullet.request;

import androidx.annotation.Keep;

import java.util.HashMap;
@Keep
public interface OnSuccessListener<T> {
    /**
     * 请求到数据，并且解析成功的回调方法
     *
     * @param targetObject         解析的核心目标Object
     * @param mapObjects   当json有多个节点时 除了t以外，其他节点存储到该map中，业务端根据节点名字查找所需对象
     */
    void onSuccess(T targetObject, HashMap<String,Object> mapObjects);

}