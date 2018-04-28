package com.quark.cobra.posp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
public class ReactorConfig {
    @Bean
    Environment env() {
//        在项目resources 资源文件夹内，创建 META-INF/reactor/ 文件夹，并把配置文件放入该文件夹
//        System.setProperty("reactor.profiles.active","reactor-environment");
        return Environment.initializeIfEmpty()
            .assignErrorJournal();
    }

    @Bean
    public EventBus eventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }
}
