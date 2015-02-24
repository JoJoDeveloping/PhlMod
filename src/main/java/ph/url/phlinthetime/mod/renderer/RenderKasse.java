package ph.url.phlinthetime.mod.renderer;

import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.model.ModelKasse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderKasse extends TileEntitySpecialRenderer {

	ModelKasse model = new ModelKasse();
	ResourceLocation res = (new ResourceLocation(ModPhantasialand.MODID+":textures/blocks/kasse.png"));
	//ResourceLocation display = (new ResourceLocation(ModPhantasialand.MODID+":textures/blocks/kasseD.png"));
	
	public RenderKasse() {
		
	}

	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(var2+0.5D, var4+1.5D, var6+0.5D);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		GL11.glRotatef(90F*var1.getBlockMetadata(), 0F, 1F, 0F);
		GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
		model.renderModel(0.0625F);
		GL11.glPopMatrix();
		/*GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(display);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
        char c0 = 61680;
        int j = c0 % 65536;
        int k = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		model.renderModel(0.0625F);
	    GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();*/
		GL11.glPopMatrix();
	}

}
