package com.wzw.nettylog4j2.server;

public interface NettyServer {
    //运行
    public void run() throws InterruptedException;

    //连接和重连都调用这个
    public void connect() throws InterruptedException ;
}
