package ph.url.phlinthetime.mod.renderer;

import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderTicketsystem implements IItemRenderer {
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;// item.getItem().equals(Item.getItemFromBlock(ModPhantasialand.blockTrack)) && type.equals(ItemRenderType.);
	}


	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		// TODO Auto-generated method stub
		return true;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		//GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTicketsystem(null), 0, 0, 0, 0F);
		GL11.glPopMatrix();
		
	}

}
