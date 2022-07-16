package com.zfoo.fivechess.config;

import com.zfoo.net.config.model.NetConfig;
import com.zfoo.orm.model.config.OrmConfig;
import com.zfoo.storage.model.config.StorageConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AutoConfiguration {
    @Bean
    public StorageConfig storageConfig(Environment environment) {
        var storageConfig = new StorageConfig();
        storageConfig.setId(environment.getProperty("storage.id"));
        storageConfig.setScanPackage(environment.getProperty("storage.scan.package"));
        storageConfig.setResourceLocation(environment.getProperty("storage.resource.location"));
        return storageConfig;
    }


    @Bean
    @ConfigurationProperties(prefix = "orm")
    public OrmConfig ormConfig() {
        return new OrmConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "net")
    public NetConfig netConfig() {
        return new NetConfig();
    }
}
