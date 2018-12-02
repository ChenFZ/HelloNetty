/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HelloServerInitializer
 * Author:   chenfz
 * Date:     2018/12/2 10:52
 * Description: 初始化器，channal注册后，会执行里面相应的初始化方法
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.imooc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 〈一句话功能简述〉<br> 
 * 〈初始化器，channal注册后，会执行里面相应的初始化方法〉
 *
 * @author chenfz
 * @create 2018/12/2
 * @since 1.0.0
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过channel获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 通过管道添加handler
        // HttpServerCodec netty自己提供的编解码助手类，可以理解为拦截器
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        // 添加自定义助手类，返回“hello netty”
        pipeline.addLast("CustomHandler",new CustomHandler());
    }
}
