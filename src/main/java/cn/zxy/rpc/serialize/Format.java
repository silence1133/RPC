package cn.zxy.rpc.serialize;

/**
 * 将对象封装成报文
 * @author Silence
 * @Date 2017/6/10
 */
public interface Format {
    /**
     * 将请求的相关参数封装成请求报文
     * @param clazz 请求的类
     * @param method 请求的方法
     * @param param 请求的参数
     * @return
     */
    String reqFormat(Class clazz,String method,Object param);

    /**
     * 将请求响应的对象封装成报文
     * @param param 响应的对象
     * @return
     */
    String respFormat(Object param);
}
