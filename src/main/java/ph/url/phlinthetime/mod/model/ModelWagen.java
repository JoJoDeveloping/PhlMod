// Date: 20.09.2014 22:36:24
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package ph.url.phlinthetime.mod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWagen extends ModelBase
{
  //fields
    ModelRenderer StutzeR;
    ModelRenderer SitzRD;
    ModelRenderer SitzRU;
    ModelRenderer StutzeL;
    ModelRenderer SitzLD;
    ModelRenderer SitzLU;
    ModelRenderer VerdeckL;
    ModelRenderer VerdeckR;
    ModelRenderer Verbindung;
  
  public ModelWagen()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      
      StutzeR = new ModelRenderer(this, 0, 0);
      StutzeR.mirror = true;
      StutzeR.addBox(-8F, 0F, -8F, 16, 32, 16);
      StutzeR.setRotationPoint(-12F, -16F, 16F);
      StutzeR.setTextureSize(128, 128);
      StutzeR.mirror = true;
      setRotation(StutzeR, 0F, 0F, 0F);
      StutzeR.mirror = false;
      SitzRD = new ModelRenderer(this, 64, 16);
      SitzRD.mirror = true;
      SitzRD.addBox(-8F, -4F, -8F, 16, 8, 16);
      SitzRD.setRotationPoint(-12F, 20F, 0F);
      SitzRD.setTextureSize(128, 128);
      SitzRD.mirror = true;
      setRotation(SitzRD, 0F, 0F, 0F);
      SitzRD.mirror = false;
      SitzRU = new ModelRenderer(this, 64, 0);
      SitzRU.mirror = true;
      SitzRU.addBox(-8F, -4F, -4F, 16, 8, 8);
      SitzRU.setRotationPoint(-12F, 12F, 4F);
      SitzRU.setTextureSize(128, 128);
      SitzRU.mirror = true;
      setRotation(SitzRU, 0F, 0F, 0F);
      SitzRU.mirror = false;
      StutzeL = new ModelRenderer(this, 0, 0);
      StutzeL.addBox(-8F, 0F, -8F, 16, 32, 16);
      StutzeL.setRotationPoint(12F, -16F, 16F);
      StutzeL.setTextureSize(128, 128);
      StutzeL.mirror = true;
      setRotation(StutzeL, 0F, 0F, 0F);
      SitzLD = new ModelRenderer(this, 64, 16);
      SitzLD.addBox(-8F, -4F, -8F, 16, 8, 16);
      SitzLD.setRotationPoint(12F, 20F, 0F);
      SitzLD.setTextureSize(128, 128);
      SitzLD.mirror = true;
      setRotation(SitzLD, 0F, 0F, 0F);
      SitzLU = new ModelRenderer(this, 64, 0);
      SitzLU.addBox(-8F, -4F, -4F, 16, 8, 8);
      SitzLU.setRotationPoint(12F, 12F, 4F);
      SitzLU.setTextureSize(128, 128);
      SitzLU.mirror = true;
      setRotation(SitzLU, 0F, 0F, 0F);
      VerdeckL = new ModelRenderer(this, 0, 48);
      VerdeckL.addBox(-6F, -2F, -8F, 12, 4, 16);
      VerdeckL.setRotationPoint(14F, -18F, 16F);
      VerdeckL.setTextureSize(128, 128);
      VerdeckL.mirror = true;
      setRotation(VerdeckL, 0F, 0F, 0F);
      VerdeckR = new ModelRenderer(this, 0, 48);
      VerdeckR.mirror = true;
      VerdeckR.addBox(-6F, -2F, -8F, 12, 4, 16);
      VerdeckR.setRotationPoint(-14F, -18F, 16F);
      VerdeckR.setTextureSize(128, 128);
      VerdeckR.mirror = true;
      setRotation(VerdeckR, 0F, 0F, 0F);
      VerdeckR.mirror = false;
      Verbindung = new ModelRenderer(this, 64, 40);
      Verbindung.addBox(-4F, -1F, -8F, 8, 2, 16);
      Verbindung.setRotationPoint(0F, -15F, 16F);
      Verbindung.setTextureSize(128, 128);
      Verbindung.mirror = true;
      setRotation(Verbindung, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    StutzeR.render(f5);
    SitzRD.render(f5);
    SitzRU.render(f5);
    StutzeL.render(f5);
    SitzLD.render(f5);
    SitzLU.render(f5);
    VerdeckL.render(f5);
    VerdeckR.render(f5);
    Verbindung.render(f5);
  }
  
  public void renderEntity(float f5){
	  
	    StutzeR.render(f5);
	    SitzRD.render(f5);
	    SitzRU.render(f5);
	    StutzeL.render(f5);
	    SitzLD.render(f5);
	    SitzLU.render(f5);
	    VerdeckL.render(f5);
	    VerdeckR.render(f5);
	    Verbindung.render(f5);
	  
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}