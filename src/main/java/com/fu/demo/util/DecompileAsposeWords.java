package com.fu.demo.util;

import javassist.*;

public class DecompileAsposeWords {
    public static void main(String[] args) throws Exception {
        //这一步是完整的jar包路径
        ClassPool.getDefault().insertClassPath("D:/Workspace/demo/src/main/resources/file/aspose-words-21.1.0-jdk17.jar");
        CtClass zzZJJClass = ClassPool.getDefault().getCtClass("com.aspose.words.zzZE0");
        CtMethod zzZ4h = zzZJJClass.getDeclaredMethod("zzZ4h");
        CtMethod zzZ4g = zzZJJClass.getDeclaredMethod("zzZ4g");
        zzZ4h.setBody("{return 1;}");
        zzZ4g.setBody("{return 1;}");
        //反编译后的“zzZE0.class"保存目录[xxx/com/aspose/words/zzZE0.class]
        zzZJJClass.writeFile("D:/Workspace/z");
    }

}
