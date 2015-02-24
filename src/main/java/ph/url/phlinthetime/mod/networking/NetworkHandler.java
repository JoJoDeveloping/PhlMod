package ph.url.phlinthetime.mod.networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.Values;
import ph.url.phlinthetime.mod.gui.ContainerEditKasse;
import ph.url.phlinthetime.mod.gui.ContainerKasse;
import ph.url.phlinthetime.mod.money.MoneyManager;
import ph.url.phlinthetime.mod.money.PlayerInfo;
import ph.url.phlinthetime.mod.networking.packets.MainPacket;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public class NetworkHandler {

	 @SubscribeEvent
	 public void onServerPacket(ServerCustomPacketEvent e) {
		 byte[] data = e.packet.payload().array();
		 if(data[1] != data.length&&(data[1]!=-1&&(data[0]==Values.PACKET_CHANGE_PRICE||data[0]==Values.PACKET_TICKETSYSTEM_CLICK))){
			 System.err.println("Error while handling Packet, recived length didnt match said! recieved: "+data[1]+" actual: "+data.length);
			 try{
			 throw new IOException("Recieved packet length didnt match actual packet length!");
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
		 }
		 switch (data[0]){
		 case Values.PACKET_TICKETSYSTEM_CLICK:
			 {
		 	 int x = 0;
			 int y = 0;
			 int z = 0;
			 String playerUUID = null;
			 DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
			 byte sig = 0;
			 byte len = 0;
			 try{
			 sig=dis.readByte();
			 len=dis.readByte();
			 x = dis.readInt();
			 y = dis.readInt();
			 z = dis.readInt();
			 playerUUID = dis.readUTF();
			 if(sig!=Values.PACKET_TICKETSYSTEM_CLICK)throw new IOException("Signature or Length didnt match!!");
			 NetHandlerPlayServer handler = (NetHandlerPlayServer) e.handler;
			 EntityPlayerMP player = handler.playerEntity;
			 if(!handler.playerEntity.getUniqueID().toString().equals(playerUUID))throw new IOException("The UUID of the Player who send this Packet is not the UUID of one the packet is about!!");
			 if(player.worldObj.getTileEntity(x, y, z) instanceof TileEntityTicketsystem&&player.inventory.getCurrentItem()!=null&&player.inventory.getCurrentItem().getItem()!=ModPhantasialand.itemRotator){
				 ItemStack is = ((TileEntityTicketsystem)player.worldObj.getTileEntity(x, y, z)).checkTicketAndOpenIfGiven(player.inventory.getCurrentItem(), player);

				 player.inventory.setInventorySlotContents(player.inventory.currentItem, is);

			 }
			 }catch(Exception ee){
				 ee.printStackTrace();
			 }
			 }
			 break;
		 case Values.PACKET_TRAIN_UPDTAE:
			 	 int x = 0;
				 int y = 0;
				 int z = 0;
				 byte length = 0;
				 try {
				 DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
				 byte sig = dis.readByte();
				 byte len = dis.readByte();
				 x = dis.readInt();
				 y = dis.readInt();
				 z = dis.readInt();
				 dis.close();
				 if(sig != 2||len != 14){
					 throw new IOException("Signature or Length didnt match!!");
				 }
				  
				 NetHandlerPlayServer handler = (NetHandlerPlayServer)e.handler; 
				 World world = handler.playerEntity.worldObj; 
				 TileEntity te_ = world.getTileEntity(x, y, z);
				 if(te_ instanceof TileEntityTrack){
					 TileEntityTrack te = (TileEntityTrack) te_;
					 ByteArrayOutputStream baos = new ByteArrayOutputStream();
						DataOutputStream dos = new DataOutputStream(baos);
							dos.writeByte(Values.PACKET_TRAIN_POSROT_CHANGED);
							dos.writeByte(16);
							dos.writeByte(te.getRotY());
							dos.writeByte(te.getSteigung());
							dos.writeInt(x);
							dos.writeInt(y);
							dos.writeInt(z);
							dos.close();
							ModPhantasialand.networkCon.sendToDimension(new MainPacket(baos.toByteArray(), ModPhantasialand.MODID), handler.playerEntity.dimension);
						
				 }else{
					 throw new IOException("Recieved coords are wrong.");
				 }
				 } catch (IOException ex) {
						ex.printStackTrace();
				 }
				 break;
		 case Values.PACKET_CHANGE_PRICE:
			 try {
			 DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
			 byte sig = dis.readByte();
			 byte len = dis.readByte();
			 int winID = dis.readInt();
			 int isnr = dis.readInt();
			 int newPrice = dis.readInt();
			 dis.close();
			 NetHandlerPlayServer handler = (NetHandlerPlayServer)e.handler; 
			 Container c = handler.playerEntity.openContainer;
			 if(c instanceof ContainerEditKasse&&handler.playerEntity.currentWindowId==winID){
				 ContainerEditKasse kasse = (ContainerEditKasse) c;
				 kasse.setItemPrice(isnr, newPrice);
			 }
			 if(sig != 3){
				 throw new IOException("Signature didnt match!!");
			 }
				
			 } catch (IOException ex) {
					ex.printStackTrace();
			 }
			 break;
				
		 }
	 }
	 @SubscribeEvent
	 public void onClientPacket(ClientCustomPacketEvent e) {
		 byte[] data = e.packet.payload().array();
		 if(data[1] != data.length){
			 System.err.println("Error while handling Packet, recived length didnt match said! recieved: "+data[1]+" actual: "+data.length);
			 try{
			 throw new IOException("Recieved packet length didnt match actual packet length!");
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
		 }
		 switch (data[0]){
		 case Values.PACKET_TRAIN_POSROT_CHANGED:
			 int x = 0;
			 int y = 0;
			 int z = 0;
			 int rotY = 0;
			 int steigung = 0;
			 byte length = 0;
			 DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
			 try{
			 byte sig = dis.readByte();
			 byte len = dis.readByte();
			 rotY = dis.readByte();
			 steigung = dis.readByte();
			 x = dis.readInt();
			 y = dis.readInt();
			 z = dis.readInt();
			 dis.close();
			 if(sig != 1||len != 16){
				 throw new IOException("Signature or Length didnt match!!");
			 }
			 
			 NetHandlerPlayClient handler = (NetHandlerPlayClient)e.handler;
			 
			 Field f = handler.getClass().getDeclaredField("clientWorldController");
			 f.setAccessible(true);
			 WorldClient world = (WorldClient) f.get(handler);
			 TileEntity te_ = world.getTileEntity(x, y, z);
			 if(te_ instanceof TileEntityTrack){
				 TileEntityTrack te = (TileEntityTrack) te_;
				 te.setSteigungAndRotaton2(steigung, rotY);
				 
			 }else{
				 throw new IOException("Recieved coords are wrong.");
			 }
			 
		 	 }catch(IOException ex){
				 ex.printStackTrace();
			 } catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			break;
		 }
	 }
	 

	
}
