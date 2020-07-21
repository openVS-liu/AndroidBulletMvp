package  com.bullet.request;

import android.util.Log
import androidx.annotation.Keep
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.Buffer
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit
@Keep
private class Courier : IRequest {
    var logTag: String = "request"
    var callback: RequestCallBack?=null

    var logEnable: Boolean = true

    lateinit var request: Request
    var job: Job? = null

    fun cancel() {
        try {
            if (job?.isActive!!) {
                job?.cancel()
            }
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }

    }

    override fun sendRequest() {
        job = CoroutineScope(IO).launch {
            try {
                var response = client?.newCall(request)?.execute()

                if (response != null) printResponse(response)
                if (response!!.isSuccessful) {
                    callback?.success(response.body()!!.string())

                } else {
                    callback?.serverError(response.code())
                }
                if (logEnable) {
                    printResponse(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback?.connectionError(e)
            }
        }
        if (logEnable) printRequest(request)
    }

    private fun printRequest(request: Request) {
        if (logEnable) {
            val requestBody = request.body()
            var body: String? = null
            requestBody?.let {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                var charset: Charset? = UTF8
                val contentType = requestBody.contentType()
                contentType?.let {
                    charset = contentType.charset(UTF8)
                }
                body = buffer.readString(charset!!)
            }

            Log.d("$logTag-Request",
                    "发送请求: method：" + request.method()
                            + "\nurl：" + request.url()
                            + "\n请求头：" + request.headers()
                            + "\n请求参数: " + body)
        }
    }

    private fun printResponse(response: Response) {
        val startNs = System.nanoTime()
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val rBody: String

        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        var charset: Charset? = UTF8
        val contentType = responseBody.contentType()
        contentType?.let {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
                Log.e(logTag, e.message)
            }
        }
        rBody = buffer.clone().readString(charset!!)

        Log.d("$logTag-Response",
                "收到响应: code:" + response.code()
                        + "\n请求url：" + response.request().url()
                        + "\nResponse: " + rBody)
    }

    interface RequestCallBack{
        fun success(result:String):Unit
        fun serverError(code:Int):Unit
        fun connectionError(exception: java.lang.Exception):Unit

    }
    companion object {
        val mainScope = MainScope()
        private val UTF8 = Charset.forName("UTF-8")
        fun runMainThreadTask( runnable: Runnable){
            mainScope.launch {
                runnable.run()
            }
        }
        private var client: OkHttpClient? = null
            get() {
                if (field == null) {
                    field = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).build()
                }
                return field
            }

        fun getOkhttpClient(): OkHttpClient {
            if (client == null) {
                client = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build()

            }
            return client!!
        }
    }
}









