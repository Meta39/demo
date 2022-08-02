package com.fu.demo.base;

import com.fu.demo.entity.Err;
import com.fu.demo.mongo.Apple;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashSet;

/**
 * ==和equals的区别
 */
@Slf4j
public class JavaEqualsHashCode {

    /**
     * ==号与equals的区别:
     * 对于基本类型，== 判断两个值是否相等，基本类型没有 equals() 方法。
     * 对于引用类型，== 判断两个变量是否引用同一个对象，而 equals() 判断引用的对象是否等价。
     * 如：Err未重写equals和hashCode方法，因此equals输出是false，Hash值不一致HashSet长度是2。
     * 如：Apple使用@Data注解，重写equals和hashCode方法，因此输出equals是true，由于HashSet无序，不允许重复数据的特性，添加到HashSet再输出长度是1，证明了2个新建的对象的Hash值也是一致的。
     */
    @Test
    public void equalsEntity(){
        //Err未重写equals和hashCode方法
        Err err1 = new Err(1,"err","msg","path");
        Err err2 = new Err(1,"err","msg","path");
        log.info("未重写equals和hashCode方法");
        log.info("{}",err1.equals(err2));
        HashSet<Err> errHashSet = new HashSet<>(2);
        errHashSet.add(err1);
        errHashSet.add(err2);
        log.info("{}",errHashSet.size());

        log.info("======================");

        //Apple使用@Data重新equals和hashCode
        Apple apple1 =new Apple(1L,"apple");
        Apple apple2 =new Apple(1L,"apple");
        log.info("重写equals和hashCode方法");
        log.info("{}",apple1.equals(apple2));
        HashSet<Apple> hashSet = new HashSet<>(2);
        hashSet.add(apple1);
        hashSet.add(apple2);
        log.info("{}",hashSet.size());
    }
}
