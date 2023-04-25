package phray.sccc.own.demo;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;

/**
 * @author shencheng4
 * @Version JavaAssistDemo.java, v 0.1 2023-04-24 17:33 shencheng4 Exp $
 */
public class JavaAssistDemo {
    public static void main(String[] args) throws Exception {
        new AopDemoService4CGLib();
        /*// 获取ClassPool
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("phray.sccc.own.demo.AopDemoService4CGLib");
        // 获取sayHelloFinal方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("sayHelloFinal");
        // 方法前后进行增强
        ctMethod.insertBefore("{ System.out.println(\"start(By JavaAssist)\");}");
        ctMethod.insertAfter("{ System.out.println(\"end(By JavaAssist)\"); }");
        // CtClass对应的字节码加载到JVM里
        Class c = ctClass.toClass();
        //反射生成增强后的类
        AopDemoService4CGLib aopDemoService4CGLib = (AopDemoService4CGLib) c.newInstance();
        aopDemoService4CGLib.sayHelloFinal();*/
    }

}
