package cn.zxy.rpc.serialize.json;

import cn.zxy.rpc.serialize.Parser;
import cn.zxy.rpc.serialize.Request;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Silence
 * @Date 2017/6/10
 */
@Slf4j
public class JsonParser implements Parser {
    public static final Parser parser = new JsonParser();
    public Request reqParse(String param) {
        return JSON.parseObject(param,Request.class);
    }

    public <T> T respParse(String result) {
        return (T)JSON.parseObject(result);
    }
}
