package netty.spring.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
@ChannelHandler.Sharable
public class ServerInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] reg = new byte[buf.readableBytes()];
        buf.readBytes(reg);
        String body = new String(reg, "UTF-8");
        System.out.println(body);
        String respMsg = "";
        ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
        ctx.fireChannelRead(buf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
