/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ChatHandler
 * Author:   chenfz
 * Date:     2018/12/2 17:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.imooc.netty.WebSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈处理消息的handler〉
 *
 * @author chenfz
 * @create 2018/12/2
 * @since 1.0.0
 *
 * TextWebSocketFrame： 在netty中，是专门用于为websocket处理文本的对象，frame是消息载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {


        final String content = msg.text();
        System.out.println("接收到的数据："+content);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Channel channel: clients){
            channel.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]"+df.format(new Date())+"-- 消息为："+content));
        }

        // 也可以这么写
//        clients.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]"+Calendar.DATE+"-- 消息为："+content));

    }

    /**
     * 当客户端连接服务端后，获取客户端的channel，放到chanelGroup中管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 以下过程会自动执行
//        clients.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的长id---"+ctx.channel().id().asLongText());
        System.out.println(ctx.channel().id().asShortText());
    }
}
