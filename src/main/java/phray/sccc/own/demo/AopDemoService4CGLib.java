package phray.sccc.own.demo;

/**
 * @author shencheng4
 * @Version AopDemoService4CGLib.java, v 0.1 2023-04-24 15:10 shencheng4 Exp $
 */
public class AopDemoService4CGLib {
    public void sayHello() {
        System.out.println("hello(normal)");
    }

    // final无法被重写
    public final void sayHelloFinal() {
        System.out.println("hello(final)");
    }
}