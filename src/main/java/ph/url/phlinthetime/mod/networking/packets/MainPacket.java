package ph.url.phlinthetime.mod.networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class MainPacket extends FMLProxyPacket {

	public MainPacket(byte[] payload, String channel)
    {
        super(Unpooled.wrappedBuffer(payload), channel);
    }

}
