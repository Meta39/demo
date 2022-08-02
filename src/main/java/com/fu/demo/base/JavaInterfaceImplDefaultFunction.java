package com.fu.demo.base;

import org.junit.Test;

/**
 * 调用接口中的默认方法
 */
public class JavaInterfaceImplDefaultFunction {

    @Test
    public void interfaceImplDefaultFunction(){
        JavaInterfaceImpl javaInterfaceImpl = new JavaInterfaceImpl();
        //调用接口中的print()默认方法
        javaInterfaceImpl.print();
    }

}
