package phray.sccc.own.demo;

import java.lang.instrument.Instrumentation;

/**
 * @author shencheng4
 * @Version JavaAssistAgentDemo.java, v 0.1 2023-04-24 17:58 shencheng4 Exp $
 */
public class JavaAssistAgentDemo {
    /**
     * JVM进程启动时若参数中指定了该Agent，则会触发transform接口对类进行增强
     *
     * @param agentArgs
     * @param instrumentation
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyClassTransformer(), true);
    }
}
