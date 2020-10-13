package netty.spring.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import netty.spring.handler.ServerInboundGetTimeHandler;
import netty.spring.handler.ServerInboundHandler;
import netty.spring.handler.ServerLastOutboundHandler;
import netty.spring.handler.ServerOutboundHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Resource
    private ServerInboundHandler serverInboundHandler;

    @Resource
    private ServerInboundGetTimeHandler serverInboundGetTimeHandler;

    @Resource
    private ServerOutboundHandler serverOutboundHandler;

    @Resource
    private ServerLastOutboundHandler serverLastOutboundHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(serverLastOutboundHandler);
        pipeline.addLast(serverOutboundHandler);
        pipeline.addLast(serverInboundHandler);
        pipeline.addLast(serverInboundGetTimeHandler);
    }
}
