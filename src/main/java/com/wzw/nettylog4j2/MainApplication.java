package com.wzw.nettylog4j2;

import com.wzw.nettylog4j2.client.*;
import com.wzw.nettylog4j2.server.NettyServer;
import com.wzw.nettylog4j2.server.NettyServer01;
import com.wzw.nettylog4j2.server.NettyServer02;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;
import java.util.HashMap;

@SpringBootApplication(scanBasePackages = {"com.wzw.nettylog4j2"})
@Data
public class MainApplication {
    public static final HashMap<String, NettyClient> nettyClientHashMap = new HashMap<>();
    public static final HashMap<String, NettyServer> nettyServerHashMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
        NettyClient01 nettyClient01 = run.getBean("nettyClient01",NettyClient01.class);
        NettyClient02 nettyClient02 = run.getBean("nettyClient02",NettyClient02.class);
        NettyClient03 nettyClient03 = run.getBean("nettyClient03",NettyClient03.class);
        NettyClient04 nettyClient04 = run.getBean("nettyClient04",NettyClient04.class);
        nettyClientHashMap.put("nettyClient01",nettyClient01);
        nettyClientHashMap.put("nettyClient02",nettyClient02);
        nettyClientHashMap.put("nettyClient03",nettyClient03);
        nettyClientHashMap.put("nettyClient04",nettyClient04);
        Collection<NettyClient> values = nettyClientHashMap.values();
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            Runnable runnable = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    if (finalI == 0) {
                        nettyClient01.run();
                    }if (finalI == 1) {
                        nettyClient02.run();
                    }if (finalI == 2) {
                        nettyClient03.run();
                    }if (finalI == 3) {
                        nettyClient04.run();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.run();
        }
        NettyServer01 nettyServer01 = run.getBean("nettyServer01", NettyServer01.class);
        NettyServer02 nettyServer02 = run.getBean("nettyServer02", NettyServer02.class);
        nettyServerHashMap.put("nettyServer01",nettyServer01);
        nettyServerHashMap.put("nettyServer02",nettyServer02);
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                Runnable runnable = new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        nettyServer01.run();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.run();
            }
            if (i == 1){
                Runnable runnable = new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        nettyServer02.run();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.run();
            }
        }
    }
}
