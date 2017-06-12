package cn.zxy.rpc.proxy;

import cn.zxy.rpc.container.Container;
import cn.zxy.rpc.container.HttpContainer;
import cn.zxy.rpc.invoke.HttpInvoker;
import cn.zxy.rpc.invoke.Invoker;
import cn.zxy.rpc.invoke.ProviderConfig;
import cn.zxy.rpc.serialize.Format;
import cn.zxy.rpc.serialize.Parser;
import cn.zxy.rpc.serialize.Request;
import cn.zxy.rpc.serialize.json.JsonFormat;
import cn.zxy.rpc.serialize.json.JsonParser;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册
 *
 * @author Silence
 * @Date 2017/6/10
 */
public class ProviderProxyFactory extends AbstractHandler {

    private Map<Class,Object> providers = new ConcurrentHashMap<Class, Object>();

    private static ProviderProxyFactory factory;

    private Parser parser = JsonParser.parser;

    private Format format = JsonFormat.format;

    private Invoker invoker = HttpInvoker.invoker;

    public ProviderProxyFactory(Map<Class,Object> providers)
    {
        if (Container.container == null)
        {
            new HttpContainer(this).start();
        }
        for (Map.Entry<Class,Object> entry: providers.entrySet())
        {
            register(entry.getKey(), entry.getValue());
        }
        factory = this;
    }

    public ProviderProxyFactory(Map<Class,Object> providers,ProviderConfig providerConfig)
    {
        if (Container.container == null)
        {
            new HttpContainer(this,providerConfig).start();
        }
        for (Map.Entry<Class,Object> entry: providers.entrySet())
        {
            register(entry.getKey(), entry.getValue());
        }
        factory = this;
    }

    public void register(Class clazz,Object object)
    {
        providers.put(clazz,object);
    }


    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException
    {
        String reqStr = request.getParameter("data");
        try
        {
            //将请求参数解析
            Request rpcRequest = parser.reqParse(reqStr);
            //反射请求
            Object result = rpcRequest.invoke(ProviderProxyFactory.getInstance().getBeanByClass(rpcRequest.getClazz()));
            //相应请求
            invoker.response(format.respFormat(result),response.getOutputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public Object getBeanByClass(Class clazz)
    {
        Object bean =  providers.get(clazz);
        return bean;
    }

    public static ProviderProxyFactory getInstance()
    {
        return factory;
    }
}
