package com.zfoo.fivechess.single.boot;

import com.zfoo.util.ThreadUtils;
import org.junit.Ignore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;

@Ignore
@SpringBootApplication(exclude = {
        // 排除MongoDB自动配置
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoReactiveAutoConfiguration.class,
        MongoReactiveDataAutoConfiguration.class,
        MongoReactiveRepositoriesAutoConfiguration.class,

        TaskExecutionAutoConfiguration.class,
        TaskSchedulingAutoConfiguration.class
})
public class ApplicationTest {
    static {
        var profile = "spring.profiles.active";
        if (System.getProperty(profile) == null) {
            //设置系统变量
            System.setProperty(profile, "dev");
        }
    }

    public static void main(String[] args) {
        Application.main(args);

        ThreadUtils.sleep(Long.MAX_VALUE);
    }
}
