package com.example.netdemo.request

import com.bullet.request.Container
import com.bullet.request.OnFinishListener
import com.bullet.request.Parser
import com.bullet.request.RequestClient
import okhttp3.Request
import java.lang.StringBuilder

/**
 * 自定义RequestClient的demo
 */
class StructRequestClient : RequestClient() {
    var container: Container? = null
    var showLoadingView = true

    /**
     * 结合自己的业务实际需求，构建Request对象。构建Request对象的构建方法请参照okHttp文档
     */
    override fun createRequest(): Request {
        val sb = StringBuilder()
        if (!url.startsWith("http")) {
            sb.append(serverUrl)
        }
        sb.append(url)
        var buider = Request.Builder()
        if (parameters != null && parameters.size > 0) {
            var index = 0
            for ((key, value) in parameters) {
                sb.append(if (index == 0) '?' else "&").append(key).append('=').append(value)
                index++
            }
        }
        buider.url(sb.toString())
        if (headers != null && headers.size > 0) {
            for ((key, value) in headers) {
                buider.addHeader(key, value)
            }
        }
        return buider.build();
    }

    /**
     * 返回数据解析器，根据自己业务的数据格式定义
     */
    override fun getParser(): Parser {
        return StructParser()
    }
    /**
     * 返回接口请求时所用的服务器域名
     */
    override fun getServerUrl(): String {
        return "http://api.help.bj.cn"
    }

    /**
     * 自定义发送http请求
     */
    override fun sendRequest() {
        if (showLoadingView) {
            container?.showLoadingView("")
        }
        super.sendRequest()
    }

    companion object {
        /**
         * 简化StructRequestClient对象的配置，Container对象封装了通用的设置。
         */
        fun with(container: Container): StructRequestClient {
            val client = StructRequestClient()
            client.container = container
            with(client) {
                setOnBreachAgreementListenr(container)
                setOnFailListener(container)
                if (showLoadingView) {
                    setOnFinishListener(object : OnFinishListener {
                        override fun onFinish() {
                            container.closeLoadingView()
                            container.onFinish()
                        }
                    })
                } else {
                    setOnFinishListener(container)
                }
                setLifecycle(container.getLifecycle())
                setContext(container.getContext())
            }
            return client
        }
    }
}