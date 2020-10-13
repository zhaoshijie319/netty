package netty.spring.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
@Configuration
public class ServerInboundGetTimeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] reg = new byte[buf.readableBytes()];
        buf.readBytes(reg);
        String body = new String(reg, "UTF-8");
        Date now = new Date( );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        String respMsg = body + "男：你看看现在都几点了！" + simpleDateFormat.format(now);
        ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
        ctx.write(respByteBuf);
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
