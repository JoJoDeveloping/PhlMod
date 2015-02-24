package ph.url.phlinthetime.mod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrack extends ModelBase
{
  //fields
    ModelRenderer SchieneO;
    ModelRenderer Seite2;
    ModelRenderer Oben2;
    ModelRenderer Oben1;
    ModelRenderer Unten2;
    ModelRenderer Unten1;
    ModelRenderer Seite1;
  
  public ModelTrack()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      SchieneO = new ModelRenderer(this, 0, 0);
      SchieneO.addBox(0F, 0F, 0F, 12, 7, 16);
      SchieneO.setRotationPoint(-6F, 8F, -8F);
      SchieneO.setTextureSize(64, 32);
      SchieneO.mirror = true;
      setRotation(SchieneO, 0F, 0F, 0F);
      Seite2 = new ModelRenderer(this, 0, 0);
      Seite2.addBox(0F, 0F, -8F, 1, 1, 16);
      Seite2.setRotationPoint(7F, 23F, 0F);
      Seite2.setTextureSize(64, 32);
      Seite2.mirror = true;
      setRotation(Seite2, 0F, 0F, 0F);
      Oben2 = new ModelRenderer(this, 0, 0);
      Oben2.addBox(0F, 0F, 0F, 1, 5, 2);
      Oben2.setRotationPoint(5.533333F, 14F, -1F);
      Oben2.setTextureSize(64, 32);
      Oben2.mirror = true;
      setRotation(Oben2, 0F, 0F, 0F);
      Oben1 = new ModelRenderer(this, 0, 0);
      Oben1.addBox(0F, 0F, 0F, 1, 5, 2);
      Oben1.setRotationPoint(-6.466667F, 14F, -1F);
      Oben1.setTextureSize(64, 32);
      Oben1.mirror = true;
      setRotation(Oben1, 0F, 0F, 0F);
      Unten2 = new ModelRenderer(this, 0, 0);
      Unten2.addBox(0F, 0F, 0F, 1, 6, 2);
      Unten2.setRotationPoint(6F, 18F, -1F);
      Unten2.setTextureSize(64, 32);
      Unten2.mirror = true;
      setRotation(Unten2, 0F, 0F, 0F);
      Unten1 = new ModelRenderer(this, 0, 0);
      Unten1.addBox(0F, 0F, 0F, 1, 6, 2);
      Unten1.setRotationPoint(-7F, 18F, -1F);
      Unten1.setTextureSize(64, 32);
      Unten1.mirror = true;
      setRotation(Unten1, 0F, 0F, 0F);
      Seite1 = new ModelRenderer(this, 0, 0);
      Seite1.addBox(0F, 0F, -8F, 1, 1, 16);
      Seite1.setRotationPoint(-8F, 23F, 0F);
      Seite1.setTextureSize(64, 32);
      Seite1.mirror = true;
      setRotation(Seite1, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    SchieneO.render(f5);
    Seite2.render(f5);
    Oben2.render(f5);
    Oben1.render(f5);
    Unten2.render(f5);
    Unten1.render(f5);
    Seite1.render(f5);
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

public void renderModel(float f5) {
		SchieneO.render(f5);
		Seite2.render(f5);
		Oben2.render(f5);
		Oben1.render(f5);
		Unten2.render(f5);
		Unten1.render(f5);
		Seite1.render(f5);
}

}
