package cn.zxy.rpc.invoke;

import java.io.OutputStream;

/**
 * @author Silence
 * @Date 2017/6/10
 */
public interface Invoker {
    /**
     * 调用请求
     * @param request 请求报文
     * @param consumerConfig 配置
     * @return
     */
    String request(String request,ConsumerConfig consumerConfig);

    /**
     * 请求应答
     * @param response 响应报文
     * @param outputStream 输出流
     */
    void response(String response, OutputStream outputStream);
}
