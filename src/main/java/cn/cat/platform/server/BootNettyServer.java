package cn.cat.platform.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author linlt
 * @createTime 2020/6/3 12:32
 * @description TODO
 */
@Component
public class BootNettyServer {
    public void start(int port) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new BootNettyServerInitializer())
                    /*
                     * option是设置 bossGroup，childOption是设置workerGroup
                     * netty 默认数据包传输大小为1024字节, 设置它可以自动调整下一次缓冲区建立时分配的空间大小，避免内存的浪费    最小  初始化  最大 (根据生产环境实际情况来定)
                     * 使用对象池，重用缓冲区
                     */
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 10496, 1048576))
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 10496, 1048576));

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {

        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
