package phray.sccc.own.demo;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author shencheng4
 * @Version MyClassTransformer.java, v 0.1 2023-04-24 17:52 shencheng4 Exp $
 */
public class MyClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals("phray.sccc.own.demo.AopDemoService4CGLib")) {
            return null;
        }
        try {
            System.out.println("MyClassTransformer，当前类名:" + className);
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get("phray.sccc.own.demo.AopDemoService4CGLib");
            CtMethod ctMethod = ctClass.getDeclaredMethod("sayHelloFinal");
            ctMethod.insertBefore("{ System.out.println(\"start(By JavaAssist with Instrument)\");}");
            ctMethod.insertAfter("{ System.out.println(\"end(By JavaAssist with Instrument)\"); }");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

