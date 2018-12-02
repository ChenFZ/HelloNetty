/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HelloServer
 * Author:   chenfz
 * Date:     2018/12/1 21:57
 * Description: netty服务器，接收用户请求并返回hello netty
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.imooc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈netty服务器，接收用户请求并返回hello netty〉
 *
 * @author chenfz
 * @create 2018/12/1
 * @since 1.0.0
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {
        // 构建一对主从线程组
        // 主线程组，用于接收客户的连接，但是不做任何处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组，老板线程组会把任务丢给他，让手下线程组处理任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 定义netty服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    // 设置一对线程组
                    .group(bossGroup, workerGroup)
                    // 产生通道的类型，为服务器设置nio的双向通道
                    .channel(NioServerSocketChannel.class)
                    // 设置处理从线程池的助手类初始化器
                    .childHandler(new HelloServerInitializer());

            // 启动server，设置端口号为8088，启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            // 监听关闭的通道，同步
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
