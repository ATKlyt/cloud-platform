package cn.cat.platform.server;

import cn.cat.platform.service.DeviceDataService;
import cn.cat.platform.utils.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author linlt
 * @createTime 2020/4/7 18:27
 * @description 处理消息的handler
 */
@Component
@Slf4j
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {


    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception, IOException {
        // System.out.println("channelRead:read msg:"+msg.toString());
        String message = msg.toString();
        log.info("接收到客户端消息:{}", message);
        DeviceDataService deviceDataService = SpringUtil.getBean(DeviceDataService.class);

        String returnMsg = deviceDataService.saveDataFromDevice(message);

        log.info("发送给客户端消息:{}", returnMsg);
        // 回应客户端
        ctx.write(returnMsg);

    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        // System.out.println("channelReadComplete");
        log.info("接收客户端发送的数据完成");
        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        //System.out.println("exceptionCaught");
        //cause.printStackTrace();
        log.error("netty出现异常", cause);
        ctx.close();//抛出异常，断开与客户端的连接
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelActive(ctx);
        ctx.channel().read();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inetSocketAddress.getAddress().getHostAddress();
        // 此处不能使用ctx.close()，否则客户端始终无法与服务端建立连接
        // System.out.println("channelActive:"+clientIP+ctx.name());
        log.info("新的客户端连接:{}", "客户端IP地址：" + clientIP + ", 客户端channel对应的长id"+ctx.channel().id().asLongText());
    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inetSocketAddress.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        // System.out.println("channelInactive:" + clientIP);
        log.info("客户端断开连接:{}", "客户端IP地址：" + clientIP + ", 客户端channel对应的长id"+ctx.channel().id().asLongText());
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = inetSocketAddress.getAddress().getHostAddress();
        // 超时时断开连接
        ctx.close();
        // System.out.println("userEventTriggered:" + clientIP);
        log.info("服务端读取数据超时，断开连接:{}", "客户端IP地址：" + clientIP + ", 客户端channel对应的长id"+ctx.channel().id().asLongText());

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        // System.out.println("channelRegistered");
        log.info("通道注册");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // System.out.println("channelUnregistered");
        log.info("通道注销");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        // System.out.println("channelWritabilityChanged");
        log.info("channelWritabilityChanged");
    }

}