package org.example.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.message.*;
import org.example.queue.MyQueue;

import java.util.ArrayList;
import java.util.List;

public class DemoSocketServerHandler
        extends ChannelInboundHandlerAdapter {

    public static List<Channel> consumerChannels = new ArrayList<>();

    public static List<Channel> supplierChannels = new ArrayList<>();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message;
        if (msg instanceof BlobMessage) {
            message = (BlobMessage) msg;
        } else if (msg instanceof BytesMessage) {
            message = (BytesMessage) msg;
        } else if (msg instanceof ObjectMessage) {
            message = (ObjectMessage) msg;
        } else {
            message = (TextMessage) msg;
        }
//        String str = (String) msg;
//        //client的channel发起身份认证消息，理论上这应该在channelActive里完成，但那边区分不开
//        if(str.startsWith("typeIdentify:")){
//            Channel channel = ctx.channel();
//            String substring = str.substring(13);
//            if (substring.equals("consumer")) consumerChannels.add(channel);
//            else if(substring.equals("supplier")) supplierChannels.add(channel);
//            return;
//        }

//        System.out.println("Client Address ====== " + ctx.channel().remoteAddress());
//        System.out.println(msg);
//        MyQueue.addPush(msg);
        String[] queues = message.getTargetQueue();

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

