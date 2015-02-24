package ph.url.phlinthetime.mod.renderer;

import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.model.ModelKasse;
import ph.url.phlinthetime.mod.model.ModelTicketsystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTicketsystem extends TileEntitySpecialRenderer {
	
	ModelTicketsystem model = new ModelTicketsystem();
	ResourceLocation res = (new ResourceLocation(ModPhantasialand.MODID+":textures/blocks/ts.png"));
	


	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(var2+0.5D, var4+1.5D, var6+0.5D);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		GL11.glRotatef(90F*((var1.getBlockMetadata()>>2)&3), 0F, 1F, 0F);
		GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
		model.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
