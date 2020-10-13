package netty.spring.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.spring.initializer.ServerInitializer;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class TestServer {

    @Resource
    private ServerInitializer serverInitializer;

    public void startServer() {
        System.out.println("男：我的相亲对象怎么还没到。");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture f = null;
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(serverInitializer);

            f = bootstrap.bind(8888).sync();
            f.channel().closeFuture().sync();
            System.out.println("相亲结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}