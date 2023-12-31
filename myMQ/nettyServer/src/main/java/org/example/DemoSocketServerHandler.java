package org.example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DemoSocketServerHandler
        extends ChannelInboundHandlerAdapter {

    public static List<Channel> consumerChannels = new ArrayList<>();

    public static List<Channel> supplierChannels = new ArrayList<>();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        String str = (String) msg;
        //client的channel发起身份认证消息，理论上这应该在channelActive里完成，但那边区分不开
        if(str.startsWith("typeIdentify:")){
            Channel channel = ctx.channel();
            String substring = str.substring(13);
            if (substring.equals("consumer")) consumerChannels.add(channel);
            else if(substring.equals("supplier")) supplierChannels.add(channel);
            return;
        }

        System.out.println("Client Address ====== " + ctx.channel().remoteAddress());
        System.out.println(msg);
        MyQueue.addPush(msg);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

