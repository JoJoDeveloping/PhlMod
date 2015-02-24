package ph.url.phlinthetime.mod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKasse extends ModelBase
{
  //fields
    ModelRenderer Basis;
    ModelRenderer Stuetze;
    ModelRenderer Display;
  
  public ModelKasse()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Basis = new ModelRenderer(this, 1, 1);
      Basis.addBox(0F, 0F, 0F, 14, 1, 11);
      Basis.setRotationPoint(-7F, 23F, -5F);
      Basis.setTextureSize(128, 128);
      Basis.mirror = true;
      setRotation(Basis, 0F, 0F, 0F);
      Stuetze = new ModelRenderer(this, 11, 22);
      Stuetze.addBox(0F, 0F, 0F, 2, 9, 1);
      Stuetze.setRotationPoint(-1F, 14F, 3F);
      Stuetze.setTextureSize(128, 128);
      Stuetze.mirror = true;
      setRotation(Stuetze, 0F, 0F, 0F);
      Display = new ModelRenderer(this, 64, 0);
      Display.addBox(0F, 0F, 0F, 12, 9, 1);
      Display.setRotationPoint(-6F, 10F, 2F);
      Display.setTextureSize(128, 128);
      Display.mirror = true;
      setRotation(Display, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Basis.render(f5);
    Stuetze.render(f5);
    Display.render(f5);
  }
  
  public void renderModel(float f5){
	  Basis.render(f5);
	  Stuetze.render(f5);
	  Display.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
