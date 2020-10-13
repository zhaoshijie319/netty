package netty.spring.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] reg = new byte[buf.readableBytes()];
        buf.readBytes(reg);
        String body = new String(reg,"UTF-8");
        String respMsg = body + "\n男：早知道我就不起这么早了。";
        ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
        ctx.write(respByteBuf);
        ctx.flush();
    }
}