package com.shulie.jjb.bytekit.agent;

import com.alibaba.bytekit.utils.Decompiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ByteKitTransformer implements ClassFileTransformer {

    public final String TEST_CLASS_NAME = "sun.net.www.http.HttpClient";

    public final String METHOD_NAME = "writeRequests";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String finalClassName = className.replace("/", ".");
        if (!TEST_CLASS_NAME.equals(finalClassName)) {
            return classfileBuffer;
        }
        System.out.println("class name 匹配上了 !");

        byte[] bytes = classfileBuffer;
        try {
            bytes = EnhanceUtil.enhanceClass(classfileBuffer, new String[]{METHOD_NAME}, JdkHttpClientInterceptor.class);
            System.out.println(Decompiler.decompile(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 查看反编译结果

        return bytes;
    }

}
