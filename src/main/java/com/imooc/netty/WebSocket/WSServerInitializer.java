/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: WSServerInitializer
 * Author:   chenfz
 * Date:     2018/12/2 16:33
 * Description: 初始化类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.imooc.netty.WebSocket;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.nio.charset.CharsetDecoder;

/**
 * 〈一句话功能简述〉<br>
 * 〈初始化类〉
 *
 * @author chenfz
 * @create 2018/12/2
 * @since 1.0.0
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        final ChannelPipeline pipeline = socketChannel.pipeline();

        // webSocket 基于http协议，所以需要http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 消息聚合器
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 24));

        // ========== 以上是用于支持http协议 ============

        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由：/ws
         * 本handler会帮你处理一些繁杂的事
         * 会帮你处理握手动作： handshaking (close,ping,pong)  ping+pong=心跳
         * 对于websocket来讲，都是以frames进行传输的，
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new ChatHandler());
    }


}
