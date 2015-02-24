package ph.url.phlinthetime.mod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTicketsystem extends ModelBase
{
  //fields
    ModelRenderer saeule;
    ModelRenderer trennung;
    ModelRenderer kontrollpunkt;
  
  public ModelTicketsystem()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      saeule = new ModelRenderer(this, 0, 0);
      saeule.addBox(0F, 0F, 0F, 8, 14, 16);
      saeule.setRotationPoint(-8F, 10F, -8F);
      saeule.setTextureSize(64, 64);
      saeule.mirror = true;
      setRotation(saeule, 0F, 0F, 0F);
      trennung = new ModelRenderer(this, 0, 0);
      trennung.addBox(0F, 0F, 0F, 8, 12, 2);
      trennung.setRotationPoint(0F, 12F, -1F);
      trennung.setTextureSize(64, 64);
      trennung.mirror = true;
      setRotation(trennung, 0F, 0F, 0F);
      kontrollpunkt = new ModelRenderer(this, 0, 40);
      kontrollpunkt.addBox(0F, 0F, 0F, 3, 2, 3);
      kontrollpunkt.setRotationPoint(-7F, 8F, -3F);
      kontrollpunkt.setTextureSize(64, 64);
      kontrollpunkt.mirror = true;
      setRotation(kontrollpunkt, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    saeule.render(f5);
    trennung.render(f5);
    kontrollpunkt.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void render(float f5){
	    saeule.render(f5);
	    trennung.render(f5);
	    kontrollpunkt.render(f5);
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
