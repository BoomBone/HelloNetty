package com.imooc.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //webSocket基于Http协议,所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对HttpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        //几乎在netty中的编程，都会使用到Handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        /*---------------------------以上适用于支持Http协议-----------------------------------*/

        //
        /**
         * webSocket 服务器处理的协议，用于指定给客户端连接访问的路由：/ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会把你处理握手动作:handshaking(close,ping,pong)
         * 对于webSocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new ChatHandler());
    }
}
