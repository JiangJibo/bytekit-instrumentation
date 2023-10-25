package com.shulie.jjb.bytekit.agent;

import com.alibaba.bytekit.asm.MethodProcessor;
import com.alibaba.bytekit.asm.interceptor.InterceptorProcessor;
import com.alibaba.bytekit.asm.interceptor.parser.DefaultInterceptorClassParser;
import com.alibaba.bytekit.utils.AgentUtils;
import com.alibaba.bytekit.utils.AsmUtils;
import com.alibaba.deps.org.objectweb.asm.ClassReader;
import com.alibaba.deps.org.objectweb.asm.tree.ClassNode;
import com.alibaba.deps.org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

public class EnhanceUtil {

    public static byte[] enhanceClass(ClassNode classNode, String[] targetMethodNames, Class interceptorClass) throws Exception {
        // 初始化Instrumentation
        AgentUtils.install();

        // 解析定义的 Interceptor类 和相关的注解
        DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();
        List<InterceptorProcessor> processors = interceptorClassParser.parse(interceptorClass);

        List<String> methodNameList = Arrays.asList(targetMethodNames);

        // 对加载到的字节码做增强处理
        for (MethodNode methodNode : classNode.methods) {
            if (methodNameList.contains(methodNode.name)) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor interceptor : processors) {
                    interceptor.process(methodProcessor);
                }
            }
        }

        // 获取增强后的字节码
        return AsmUtils.toBytes(classNode);
    }

    public static byte[] enhanceClass(Class targetClass, String[] targetMethodNames, Class interceptorClass) throws Exception {

        // 解析定义的 Interceptor类 和相关的注解
        DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();
        List<InterceptorProcessor> processors = interceptorClassParser.parse(interceptorClass);

        ClassNode classNode = AsmUtils.loadClass(targetClass);

        List<String> methodNameList = Arrays.asList(targetMethodNames);

        // 对加载到的字节码做增强处理
        for (MethodNode methodNode : classNode.methods) {
            if (methodNameList.contains(methodNode.name)) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor interceptor : processors) {
                    interceptor.process(methodProcessor);
                }
            }
        }

        // 获取增强后的字节码
        return AsmUtils.toBytes(classNode);
    }

    public static byte[] enhanceClass(byte[] bytes, String[] targetMethodNames, Class interceptorClass) throws Exception {

        // 解析定义的 Interceptor类 和相关的注解
        DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();
        List<InterceptorProcessor> processors = interceptorClassParser.parse(interceptorClass);

        ClassReader cr = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        cr.accept(classNode, ClassReader.SKIP_FRAMES);

        List<String> methodNameList = Arrays.asList(targetMethodNames);

        // 对加载到的字节码做增强处理
        for (MethodNode methodNode : classNode.methods) {
            if (methodNameList.contains(methodNode.name)) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor interceptor : processors) {
                    interceptor.process(methodProcessor);
                }
            }
        }

        // 获取增强后的字节码
        return AsmUtils.toBytes(classNode);
    }

}
