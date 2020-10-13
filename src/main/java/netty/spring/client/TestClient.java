package netty.spring.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.spring.initializer.ClientInitializer;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class TestClient {
    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 8888))
                    .handler(new ClientInitializer());
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
            System.out.println("相亲结束");
        } catch (Exception e) {

        } finally {
            group.shutdownGracefully();
        }
    }
}