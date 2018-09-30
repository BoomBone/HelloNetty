package com.imooc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义助手类
 */

//SimpleInboundHandler：对于请求来讲，其实相当于[入栈，入境]
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                HttpObject httpObject)
            throws Exception {
        //获取channel
        Channel channel = channelHandlerContext.channel();

        if (httpObject instanceof HttpRequest) {

            //显示客户端的远程地址
            System.out.println(channel.remoteAddress());

            //定义发送的数据消息
            ByteBuf content = Unpooled.copiedBuffer("Hello,Netty!", CharsetUtil.UTF_8);

            //构建一个http响应response
            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                            HttpResponseStatus.OK, content);

            //为响应增加数据类型和长度
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //把响应写到客户端
            channelHandlerContext.writeAndFlush(response);

        }
    }
}
