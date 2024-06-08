package org.example.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncHandler extends ChannelInboundHandlerAdapter {
    private static final String SLAVE_SERVER_HOST = "localhost:8081"; // slave服务器地址
    private static final int SLAVE_SERVER_PORT = 8081; // slave服务器端口
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private static final int REDO_TIMES = 3;
    private static final long INITIAL_BACKOFF_MS = 1000; // 初始重试间隔时间，单位毫秒

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 收到消息后，向slave服务器发送
        sendMessageToSlave(ctx, (String) msg);
    }

    private void sendMessageToSlave(ChannelHandlerContext ctx, String message) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new SlaveClientHandler(ctx)); // 自定义SlaveClientHandler处理slave的响应

        try {
            AtomicBoolean syncSucceed = new AtomicBoolean(false);
            AtomicInteger timesCount = new AtomicInteger(1);
            while (!syncSucceed.get() && timesCount.get() < REDO_TIMES) {
                ChannelFuture future = bootstrap.connect(SLAVE_SERVER_HOST, SLAVE_SERVER_PORT).sync();
                future.channel().writeAndFlush(message).addListener((ChannelFutureListener) future1 -> {
                    if (!future1.isSuccess()) {
                        System.out.println("发送失败，准备重试...");
                        // 这里简单打印错误信息，实际应用中应根据情况决定是否重试以及重试策略
                        // 实现重试逻辑
                        syncSucceed.set(true);
                        timesCount.getAndIncrement();
                        Thread.sleep(timesCount.get() * INITIAL_BACKOFF_MS); //退避
                    } else {
                        System.out.println("消息发送成功");
                    }
                });
                future.channel().closeFuture().sync();
            }
            if(timesCount.get() == REDO_TIMES){
                //跑完所有重试，进入兜底
                //暂时打个log
                System.out.println("发送失败"+message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    // 注意：SlaveClientHandler 需要单独定义以处理slave的响应
    public static class SlaveClientHandler extends ChannelInboundHandlerAdapter {

        private final ChannelHandlerContext masterCtx;

        public SlaveClientHandler(ChannelHandlerContext masterCtx) {
            this.masterCtx = masterCtx;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object slaveResponse) throws Exception {
            // 根据slave响应内容决定是否重试
            boolean shouldRetry = determineRetryBasedOnResponse((String) slaveResponse);
            if (shouldRetry) {
                // 重试逻辑，例如重新调用sendMessageToSlave
            } else {
                // 处理响应或转发给其他部分
                masterCtx.writeAndFlush("Slave响应: " + slaveResponse);
            }
        }

        private boolean determineRetryBasedOnResponse(String response) {
            // 示例逻辑，根据实际需求调整
            return !"SUCCESS".equals(response);
        }
    }
}
