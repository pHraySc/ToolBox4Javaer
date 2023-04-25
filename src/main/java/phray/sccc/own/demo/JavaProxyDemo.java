package phray.sccc.own.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author shencheng4
 * @Version JavaProxyDemo.java, v 0.1 2023-04-24 15:09 shencheng4 Exp $
 */
public class JavaProxyDemo {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //被代理的接口
        Class[] classes = new Class[]{AopDemoService.class};
        InvocationHandler handler = new DynamicHandler(new AopDemoServiceImpl());

        AopDemoService proxyService = (AopDemoService) Proxy.newProxyInstance(classLoader, classes, handler);
        proxyService.sayHello();
    }
}
