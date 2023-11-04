package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.swing.*;

public class DemoSocketClientHandler
        extends ChannelInboundHandlerAdapter {

    JTextField textField;

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        textField.setText((String) msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        ctx.channel().writeAndFlush("typeIdentify:consumer");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

