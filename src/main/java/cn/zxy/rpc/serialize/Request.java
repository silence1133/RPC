package cn.zxy.rpc.serialize;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求对象
 * @author Silence
 * @Date 2017/6/10
 */
@Data
@AllArgsConstructor
public class Request implements Serializable {
    private Class clazz;
    private String method;
    private Object param;


    public Object invoke(Object bean) throws Exception
    {
        return clazz.getMethod(method, param.getClass()).invoke(bean,param);
    }

}
