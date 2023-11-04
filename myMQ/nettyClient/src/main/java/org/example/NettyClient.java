package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        String command = args[0];
        if ("start".equals(command)) {
            System.out.println("元神，启动！！！！");
            NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(eventLoopGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                                pipeline.addLast(new DemoSocketClientHandler());
                            }
                        });
                ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
                Channel channel = future.channel();

                new ClientUI(channel); //在client角度：绑定目标broker
                channel.closeFuture().sync();
                System.out.println("channel关闭完成");
            } finally {
                if (eventLoopGroup != null) {
                    eventLoopGroup.shutdownGracefully();
                }
            }
        }
    }
}
