/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: WSServer
 * Author:   chenfz
 * Date:     2018/12/2 16:20
 * Description: 基于webSocket的实时通讯服务器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.imooc.netty.WebSocket;

import com.imooc.netty.HelloServerInitializer;
import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈基于webSocket的实时通讯服务器〉
 *
 * @author chenfz
 * @create 2018/12/2
 * @since 1.0.0
 */
public class WSServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup,subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            // 监听关闭的通道
            channelFuture.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
