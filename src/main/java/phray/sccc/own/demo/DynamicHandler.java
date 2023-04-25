package phray.sccc.own.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author shencheng4
 * @Version DynamicHandler.java, v 0.1 2023-04-24 15:08 shencheng4 Exp $
 */
public class DynamicHandler implements InvocationHandler {

    private Object target;

    public DynamicHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 编写增强逻辑
        System.out.println("start");
        Object result = method.invoke(target, args);
        System.out.println("end");
        return result;
    }
}
