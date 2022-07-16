package com.zfoo.fivechess;

import com.zfoo.event.model.event.AppStartEvent;
import com.zfoo.net.core.websocket.WebsocketServer;
import com.zfoo.util.net.HostAndPort;
import com.zfoo.util.net.NetUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;

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
public class Application {
    public static final int WEBSOCKET_SERVER_PORT = 18000;

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        context.registerShutdownHook();
        context.publishEvent(new AppStartEvent(context));

        var websocketServer = new WebsocketServer(HostAndPort.valueOf(NetUtils.getLocalhostStr(), WEBSOCKET_SERVER_PORT));
        websocketServer.start();
    }
}
