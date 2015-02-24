package ph.url.phlinthetime.mod.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.model.ModelTrack;
import ph.url.phlinthetime.mod.model.ModelTrackK;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;

public class RenderTrack extends TileEntitySpecialRenderer{
	
	  private final ModelTrack model;
	  private final ModelTrackK model2;
      ResourceLocation textures = (new ResourceLocation(ModPhantasialand.MODID+":textures/blocks/schiene.png"));
      
      public RenderTrack() {
          this.model = new ModelTrack();
          this.model2 = new ModelTrackK();
      }
     
      private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
              int meta = world.getBlockMetadata(x, y, z);
              GL11.glPushMatrix();
              GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
              GL11.glPopMatrix();
      }
     
      @Override
      public void renderTileEntityAt(TileEntity te_, double x, double y, double z, float scale) {
    	  TileEntityTrack te = (TileEntityTrack) te_;
          Minecraft.getMinecraft().renderEngine.bindTexture(textures);
    	  GL11.glPushMatrix();
          GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
          GL11.glPushMatrix();
          if(te.getRotY()%2!=1)
          {
	          GL11.glRotatef(te.getRotY()*45F, 0F, 1F, 0F);
	          GL11.glPushMatrix();
	          GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
	    	  GL11.glPushMatrix();
	          switch(te.getSteigung()){
	          case 2:
	        	  if(te.getRotY()==2||te.getRotY()==6){
	        		  GL11.glTranslatef(0F, 1F, -1F);
	        		  GL11.glRotatef(90F, 1F, 0F, 0F);
	        	  }else if(te.getRotY()==4||te.getRotY()==0){
	        		  GL11.glRotatef(90F, 1F, 0F, 0F);
	        		  GL11.glTranslatef(0F, -1F, -1F);
	        	  }
	        	  break;
	          case 4:
	        	  GL11.glRotatef(180F, 1F, 0F, 0F);
	        	  GL11.glTranslatef(0F, -2F, 0F);
	        	  break;
	          case 6:
	        	  if(te.getRotY()==2||te.getRotY()==6){
	        		  GL11.glTranslatef(0F, 1F, 1F);
	        		  GL11.glRotatef(270F, 1F, 0F, 0F);
	        	  }else if(te.getRotY()==4||te.getRotY()==0){
	        		  GL11.glRotatef(270F, 1F, 0F, 0F);
	        		  GL11.glTranslatef(0F, -1F, 1F);
	        	  }
	        	  break;
	          }
	         // System.out.println(te.getRotY());
	          this.model.renderModel(0.0625F);
	          GL11.glPopMatrix();
	          GL11.glPopMatrix();
	      }else{
	          GL11.glRotatef((te.getRotY()*45F)+45F, 0F, 1F, 0F);
	          GL11.glPushMatrix();
	          GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
	    	  GL11.glPushMatrix();
	    	  if(te.getSteigung()==4){
	    		  GL11.glRotatef(180F, 1F, 0F, 1F);
	    		  GL11.glRotatef(180F, 0F, 1F, 0F);
	    	  	  GL11.glTranslatef(0F, -2F, 0F);
	    	  }
	          this.model2.renderModel(0.0625F);
	          GL11.glPopMatrix();
	          GL11.glPopMatrix(); 
	      }
          GL11.glPopMatrix();
          GL11.glPopMatrix();
      }

      //Set the lighting stuff, so it changes it's brightness properly.      
      private void adjustLightFixture(World world, int i, int j, int k, Block block) {
              Tessellator tess = Tessellator.instance;
              //float brightness = block.getBlockBrightness(world, i, j, k);
              //As of MC 1.7+ block.getBlockBrightness() has become block.getLightValue():
              float brightness = block.getLightValue(world, i, j, k);
              int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
              int modulousModifier = skyLight % 65536;
              int divModifier = skyLight / 65536;
              tess.setColorOpaque_F(brightness, brightness, brightness);
              OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  (float) modulousModifier,  divModifier);
      }
}
