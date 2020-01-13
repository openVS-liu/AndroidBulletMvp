package com.example.netdemo.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bullet.request.Parser;


public class StructParser extends Parser {
    private static final String STATUE_NODE = "status";
    private static final String MESSAGE_NODE = "msg";
    private static final String DATA_NODE = "data";

    @Override
    public void parseData(String data) {
        try {
            if (wholeNodeClass==null){
                callBack.success(data,null);
            }
            JSONObject jsonObject = JSON.parseObject(data);
            if (jsonObject.containsKey(STATUE_NODE)) {
                int code = jsonObject.getInteger(STATUE_NODE);
                if (code == 0) {
                    String dataString = jsonObject.getString(DATA_NODE);
                    if (dataString.startsWith("[")) {
                        callBack.success(JSON.parseArray(dataString, wholeNodeClass), null);

                    } else {
                        callBack.success(JSON.parseObject(dataString, wholeNodeClass), null);

                    }
                } else {
                    callBack.breachAgreement(code, jsonObject.getString(MESSAGE_NODE));
                }
            } else {
                callBack.breachAgreement(-1, "数据异常，缺少code节点");
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.fail("数据解析出错");
        }

    }
}
