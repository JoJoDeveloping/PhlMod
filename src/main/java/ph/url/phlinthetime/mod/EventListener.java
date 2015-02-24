package ph.url.phlinthetime.mod;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import ph.url.phlinthetime.mod.block.BlockTicketsystem;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import ph.url.phlinthetime.mod.money.MoneyManager;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;

public class EventListener {
	
	@SubscribeEvent
	public void onItemPickedUp(ItemPickupEvent e){
		if(e.pickedUp.getEntityItem().getItem().getUnlocalizedName().equals(ModPhantasialand.itemCreditCard.getUnlocalizedName())){
			ItemStack is = e.pickedUp.getEntityItem();
			if(is.stackTagCompound!=null&&is.stackTagCompound.hasKey("owner")&&!(is.stackTagCompound.getString("owner").equals(e.player.getUniqueID().toString().replace("-", ""))))
				MoneyManager.instance.lockCard(is.stackTagCompound.getString("owner"));
		}
	}
	
	@SubscribeEvent
	public void onDrawBlockHighlight(DrawBlockHighlightEvent evt){
		Block b = evt.player.worldObj.getBlock(evt.target.blockX, evt.target.blockY, evt.target.blockZ);
		if(b.equals(ModPhantasialand.blockTicketsystem)){
			BlockTicketsystem block = (BlockTicketsystem) b;
			
			block.setBBRaytracing(true);
            double d0 = (double)Minecraft.getMinecraft().playerController.getBlockReachDistance();
			MovingObjectPosition mop = evt.player.rayTrace(d0, evt.partialTicks);
			//if(mop.blockX==evt.target.blockX&&mop.blockY==evt.target.blockY&&mop.blockZ==evt.target.blockZ){
			Minecraft.getMinecraft().renderGlobal.drawSelectionBox(evt.player, mop, 0, evt.partialTicks);
			block.setBBRaytracing(false);
			evt.player.rayTrace(d0, evt.partialTicks);
			evt.setCanceled(true);
			//}
		}
	}
	
	@SubscribeEvent
	public void onRenderPre(RenderTickEvent evt){
		if(evt.phase == evt.phase.START){
			if(Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.ridingEntity != null && Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityBlackMamba){
				//Minecraft.getMinecraft().gameSettings.thirdPersonView =0;
				EntityBlackMamba e = (EntityBlackMamba) Minecraft.getMinecraft().thePlayer.ridingEntity;
				TileEntity te_ = e.worldObj.getTileEntity(e.getHangingX(), e.getHangingY(), e.getHangingZ());
				if(te_ != null&&te_ instanceof TileEntityTrack){
					TileEntityTrack te = (TileEntityTrack) te_;
					te.calculatePositionRotationAndUpdate(e, false, e.getEntitySpeed()/20F*evt.renderTickTime, false);
					e.updateRiderPosition();
					e.markRenderUpdateDone();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderLivingPre(RenderLivingEvent.Pre evt){
		if(evt.entity.ridingEntity != null&&evt.entity.ridingEntity instanceof EntityBlackMamba){
			evt.entity.renderYawOffset = 0f;
			evt.entity.prevRenderYawOffset=0f;
			evt.entity.rotationYawHead = 0f;
		}
	}
		
	
}
