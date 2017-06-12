package cn.zxy.rpc.serialize.json;

import cn.zxy.rpc.serialize.Format;
import cn.zxy.rpc.serialize.Request;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Silence
 * @Date 2017/6/10
 */
public class JsonFormat implements Format {
    public static final Format format = new JsonFormat();

    public String reqFormat(Class clazz, String method, Object param) {
        Request request = new Request(clazz,method,param);
        return JSON.toJSONString(request, SerializerFeature.WriteClassName);
    }

    public String respFormat(Object param) {
        return JSON.toJSONString(param, SerializerFeature.WriteClassName);
    }
}
