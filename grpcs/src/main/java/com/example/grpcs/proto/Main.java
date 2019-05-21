package com.example.grpcs.proto;

import java.io.IOException;

/**
 * 服务端
 * @Auther: HuWeiJian
 * @Date: 2018/10/29 16:50
 * @Description:
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start(50051);
        server.blockUntilShutdown();
    }
}
