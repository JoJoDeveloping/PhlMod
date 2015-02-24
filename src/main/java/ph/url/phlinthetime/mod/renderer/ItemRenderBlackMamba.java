package ph.url.phlinthetime.mod.renderer;

import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import ph.url.phlinthetime.mod.model.ModelTrack;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ItemRenderBlackMamba implements IItemRenderer {

	private EntityBlackMamba entity;
	
	public ItemRenderBlackMamba(){
		entity = new EntityBlackMamba(null);
		
	}
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;// item.getItem().equals(Item.getItemFromBlock(ModPhantasialand.blockTrack)) && type.equals(ItemRenderType.);
	}


	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private float getRotationForTime(){
		long timefac = 20000;
		long time = System.currentTimeMillis()%timefac;
		float fac = 360F/timefac;
		float result = (time*fac);
		return result;	
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		float f = 1F/2.5F;
		GL11.glTranslatef(0F, -0.65F, 0F);
		GL11.glScalef(f,f,f);
		RenderManager.instance.getEntityRenderObject(entity).doRender(entity, 0F, 0F, 0F, getRotationForTime(), 0F);
		GL11.glTranslatef(0F, -1.5F, 0F);
		GL11.glPopMatrix();
		
	}
	
}
