package phray.sccc.own.demo;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author shencheng4
 * @Version AsmDemo.java, v 0.1 2023-04-24 17:16 shencheng4 Exp $
 */
public class AsmDemo {

    public static void main(String[] args) throws IOException {
        // 读取字节码
        ClassReader classReader = new ClassReader("phray.sccc.own.demo.AopDemoService4CGLib");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // 字节码增强
        ClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        // 输出字节码到class文件
        File f = new File("D:\\Projects\\BTCP\\ToolBox4Javaer\\target\\classes\\phray\\sccc\\own\\demo" +
                "\\AopDemoService4CGLib.class");
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(data);
        fout.close();


    }

}
