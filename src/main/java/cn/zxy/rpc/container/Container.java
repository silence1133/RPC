package cn.zxy.rpc.container;

/**
 * @author Silence
 * @Date 2017/6/10
 */
public abstract class Container {
    public static volatile boolean isStart = false;
    public abstract void start();
    public static volatile Container container = null;
}
