package ph.url.phlinthetime.mod.items;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.Values;
import ph.url.phlinthetime.mod.block.BlockTicketsystem;
import ph.url.phlinthetime.mod.networking.packets.MainPacket;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class ItemRotator extends Item {
	
	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase hitten, EntityLivingBase hitter) {
		hitten.attackEntityFrom(new DamageSource("magic"), 10000f);
		hitten.onDeath(new DamageSource("magic"));
		hitten.setDead();
		System.out.println(hitten.getClass().getName());
		return super.hitEntity(is, hitten, hitter);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if(world.isRemote){
			return false;
		}
		if(world.getBlock(x, y, z).getUnlocalizedName().equals(ModPhantasialand.blockTrack.getUnlocalizedName())&&world.getTileEntity(x, y, z) instanceof TileEntityTrack){						
			TileEntityTrack te = (TileEntityTrack) world.getTileEntity(x, y, z);
			if(player.isSneaking()){
				if(te.getRotY()%2==0)
					te.setSteigungAndRotaton(te.getSteigung()+2, te.getRotY());
				else{
					te.setSteigungAndRotaton(te.getSteigung()+4, te.getRotY());
				}
			}else{
				te.setSteigungAndRotaton(te.getSteigung(), te.getRotY() + 1);
			}
			world.markBlockForUpdate(x, y, z);
			world.notifyBlocksOfNeighborChange(x, y, z, ModPhantasialand.blockTrack);
			player.addChatMessage(new ChatComponentText("Rotation: "+te.getRotY()));
			player.addChatMessage(new ChatComponentText("Steigung: "+te.getSteigung()));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			try {
				dos.writeByte(Values.PACKET_TRAIN_POSROT_CHANGED);
				dos.writeByte(16);
				dos.writeByte(te.getRotY());
				dos.writeByte(te.getSteigung());
				dos.writeInt(x);
				dos.writeInt(y);
				dos.writeInt(z);
				dos.close();
				ModPhantasialand.networkCon.sendToDimension(new MainPacket(baos.toByteArray(), ModPhantasialand.MODID), player.dimension);
			} catch (IOException e) {
				player.addChatMessage(new ChatComponentText("§c§lAn Error occurred while updating the Rotation or §c§lSteigung: IOException: "+e.getMessage()));
				e.printStackTrace();
			}
			return true;
		}
		if(world.getBlock(x, y, z).getUnlocalizedName().equals(ModPhantasialand.blockKasse.getUnlocalizedName())&&world.getTileEntity(x, y, z) instanceof TileEntityKasse&&player.isSneaking()){
			world.setBlockMetadataWithNotify(x, y, z, (world.getBlockMetadata(x, y, z)+1)&3, 2);
			return true;
		}
		if(ModPhantasialand.config.isBeta()&&world.getBlock(x, y, z).equals(ModPhantasialand.blockTicketsystem)&&world.getTileEntity(x, y, z) instanceof TileEntityTicketsystem&&player.isSneaking()){
			int i = ((world.getBlockMetadata(x, y, z)&12)>>2);
			i++;
			if(i==4)i=0;
			world.setBlockMetadataWithNotify(x, y, z, i<<2, 3);
			return true;
		}
		return false;
	}
	
	
}
