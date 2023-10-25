package com.shulie.jjb.bytekit.agent;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtEnter;
import sun.net.www.MessageHeader;

public class JdkHttpClientInterceptor {

    // 拦截方法Entry点进行处理
    @AtEnter(inline = false, suppress = RuntimeException.class)
    public static void atEnter(@Binding.This Object object,
                               @Binding.Class Object clazz,
                               @Binding.Args Object[] args,
                               @Binding.MethodName String methodName,
                               @Binding.MethodDesc String methodDesc) {
        MessageHeader messageHeader = (MessageHeader) args[0];
        messageHeader.add("time", System.currentTimeMillis() + "");
    }

}
