package cn.zxy.rpc.serialize;

/**
 * 将报文转换为需要的对象
 * @author Silence
 * @Date 2017/6/10
 */
public interface Parser {
    /**
     * 将请求的报文转换为request对象
     * @param param 请求的报文
     * @return
     */
    Request reqParse(String param);

    /**
     * 将响应的报文转换为响应的对象
     * @param result 响应报文
     * @param <T>
     * @return
     */
    <T> T respParse(String result);
}
