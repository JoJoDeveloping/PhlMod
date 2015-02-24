package ph.url.phlinthetime.mod.renderer;

import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import ph.url.phlinthetime.mod.model.ModelWagen;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderBlackMamba extends Render {

	public ResourceLocation tex = new ResourceLocation(ModPhantasialand.MODID, "textures/entity/wagen.png");
	
	private ModelWagen model = new ModelWagen();
	
	@Override
	public void doRender(Entity e_, double x, double y, double z, float rot1, float ptt) {
		if(!(e_ instanceof EntityBlackMamba))return;
		EntityBlackMamba e = (EntityBlackMamba) e_;
		if(e.worldObj != null){
		TileEntity te_ = e.worldObj.getTileEntity(e.getHangingX(), e.getHangingY(), e.getHangingZ());
		if(te_ != null&&te_ instanceof TileEntityTrack){
			TileEntityTrack te = (TileEntityTrack) te_;
			if(!e.isRenderUpdateDone()){
			te.calculatePositionRotationAndUpdate(e, false, e.getEntitySpeed()/20F*ptt, false);
			e.updateRiderPosition();
			}else{
				//te.calculatePositionRotationAndUpdate(e, false, 0F, false);
			}
			x = e.posX - RenderManager.renderPosX;
			y = e.posY - RenderManager.renderPosY;
			z = e.posZ - RenderManager.renderPosZ;
			rot1 = e.rotationYaw;
		}}
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(rot1, 0, 1, 0);
		GL11.glRotatef(180F, 1, 0, 0);
		GL11.glRotatef(e.rotationPitch, 1, 0, 0);
		if(e.rotationPitch==180f){
			GL11.glTranslatef(0f, e.height, 0f);
			GL11.glRotatef(180F, 0f, 1f, 0f);
		}
		GL11.glTranslatef(0F, -1.5F, -0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(getEntityTexture(e));
		model.renderEntity(0.0625F);
		GL11.glPopMatrix();
		e.setWasRendered();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		return tex;
	}

}
