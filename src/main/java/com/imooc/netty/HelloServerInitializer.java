package com.imooc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册后，会执行里面的相应的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //通过SocketChannel去获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //通过管道添加Handler
        //HttpServerCodec是由netty自己提供的助手类
        //当请求到服务端，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

        //添加自定义的助手类，返回"Hello Netty~"
        pipeline.addLast("customHandler", new CustomHandler());
    }
}
