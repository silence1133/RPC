package cn.zxy.rpc.container;

import cn.zxy.rpc.invoke.ProviderConfig;
import lombok.extern.slf4j.Slf4j;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;


/**
 * @author Silence
 * @Date 2017/6/10
 */
@Slf4j
public class HttpContainer extends Container {
    private AbstractHandler httpHandler;
    private ProviderConfig providerConfig;

    public HttpContainer(AbstractHandler httpHandler)
    {
        this(httpHandler, new ProviderConfig("/invoke",8080));
    }

    public HttpContainer(AbstractHandler httpHandler,ProviderConfig providerConfig)
    {
        this.httpHandler = httpHandler;
        this.providerConfig = providerConfig;
        Container.container = this;
    }

    public void start()
    {
        Server server = new Server();
        try
        {
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(providerConfig.getPort());
            server.setConnectors(new Connector[]{
                    connector
            });
            server.setHandler(httpHandler);
            server.start();
        }
        catch (Throwable e)
        {
            log.error("fasd",e);
        }
    }

}
