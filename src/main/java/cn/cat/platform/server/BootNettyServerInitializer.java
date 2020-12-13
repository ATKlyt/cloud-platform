package cn.cat.platform.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author linlt
 * @createTime 2020/4/7 17:41
 * @description TODO
 */
@Component
public class BootNettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    BootNettyChannelInboundHandlerAdapter bootNettyChannelInboundHandlerAdapter;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // ChannelOutboundHandler，依照逆序执行
        pipeline.addLast("encoder", new StringEncoder(StandardCharsets.UTF_8));

        // 属于ChannelInboundHandler，依照顺序执行
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("GBK")));

        // 自定义ChannelInboundHandlerAdapter
        pipeline.addLast(new BootNettyChannelInboundHandlerAdapter());
    }

}
