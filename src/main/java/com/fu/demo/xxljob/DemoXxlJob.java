package com.fu.demo.xxljob;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoXxlJob {

    @XxlJob("demoXxlJob")
    public void demoXxlJob(){
        log.info("这是一个由Xxl-job调度的定时任务");
    }
}
