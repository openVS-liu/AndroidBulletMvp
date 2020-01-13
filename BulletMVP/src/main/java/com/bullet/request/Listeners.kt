package  com.bullet.request;

interface OnBreachAgreementListener {
    /**
     * 非法code回调
     *
     * @param code   json中的code节点值
     * @param msg    json中的msg节点值
     * @param client 发送本次请求的HttpRequestClient
     */
    fun onBreachAgreement(code: Int, msg: String?, client: IRequest?) //    void invoke(int code, String msg, RequestClient client);
}

interface OnFailListener {
    fun onError(msg: String?, client: IRequest)
}

interface OnFinishListener {
    /**
     * 请求结束的回调
     *
     */
    fun onFinish()
}

