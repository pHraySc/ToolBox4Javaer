package phray.sccc.own.demo;

/**
 * @author shencheng4
 * @Version AopDemoServiceImpl.java, v 0.1 2023-04-24 15:07 shencheng4 Exp $
 */
public class AopDemoServiceImpl implements AopDemoService {
    @Override
    public void sayHello() {
        System.out.println("hello from aop demo service");
    }
}
