/**
 * Project Name: my-projects
 * Package Name: com.taotao.rpc.common
 * Date: 2020/2/27 11:09
 * Author: dengtao
 */
package com.taotao.cloud.rpc.other.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <br>
 *
 * @author dengtao
 * @version v1.0.0
 * @create 2020/2/27 11:09
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> clazz;

    public RpcEncoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf out) throws Exception {
        if (clazz.isInstance(obj)) {
            byte[] bytes = SerializationUtil.serialize(obj);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
