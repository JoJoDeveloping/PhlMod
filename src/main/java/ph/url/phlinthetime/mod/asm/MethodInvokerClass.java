package ph.url.phlinthetime.mod.asm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseHelper;

public class MethodInvokerClass {

	/**
	 * 
	 * @param Field to change ( multiple names )
	 * @param Object to change field
	 * @param new Value
	 * @return true if an field was overwritten, false otherwise
	 */
	public static boolean changeFinal(String[] names, Object instance, Object value){
		boolean flag = false;
		for(String s : names){
			try {
				Field f = instance.getClass().getDeclaredField(s);
				f.setAccessible(true);
			    Field modifiersField = Field.class.getDeclaredField("modifiers");
			    modifiersField.setAccessible(true);
			    modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			    f.set(instance, value);
			    flag = true;
			} catch (NoSuchFieldException e) {
				continue;
			} catch (SecurityException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
			
		}
		return flag;
	}
	
	public static void setRotationAngles(ModelBiped thiz, float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity e)
    {
		if(e.ridingEntity != null && e.ridingEntity instanceof EntityBlackMamba&&(Minecraft.getMinecraft().thePlayer!=e||Minecraft.getMinecraft().gameSettings.thirdPersonView!=0)){
			thiz.aimedBow = false;
			thiz.isSneak=false;
			
			if((RenderManager.instance.getEntityRenderObject(e) instanceof RenderPlayer)&&((RenderPlayer)RenderManager.instance.getEntityRenderObject(e)).modelBipedMain==thiz){
				GL11.glRotatef(-e.ridingEntity.rotationYaw+((((EntityBlackMamba) e.ridingEntity).isOverHead())?180f:0f), 0f, 1f, 0f);
				GL11.glRotatef(-e.ridingEntity.rotationPitch, 1f, 0f, 0f);
				if(((EntityBlackMamba) e.ridingEntity).isOverHead()){
					GL11.glTranslatef(0f, 1f/16f*7f, 0f);
				}
			}
			thiz.bipedBody.rotateAngleX = 0f;
			thiz.bipedBody.rotateAngleY = 0f;
			thiz.bipedBody.rotateAngleZ = 0f;
			thiz.bipedBody.rotateAngleX = 0.0F;
            thiz.bipedRightLeg.rotationPointZ = 0.1F;
            thiz.bipedLeftLeg.rotationPointZ = 0.1F;
            thiz.bipedRightLeg.rotationPointY = 12.0F;
            thiz.bipedLeftLeg.rotationPointY = 12.0F;
            thiz.bipedHead.rotateAngleX = (float) ((e.ridingEntity.rotationPitch+e.rotationPitch)*Math.PI/180f);
            thiz.bipedHead.rotateAngleY = (float) ((e.ridingEntity.rotationYaw+((((EntityBlackMamba) e.ridingEntity).isOverHead())?-180f:0f)+e.rotationYaw)*Math.PI/180f)*((((EntityBlackMamba) e.ridingEntity).isOverHead())?-1:1);
            thiz.bipedHead.rotateAngleZ = 0f;
            thiz.bipedHead.rotationPointY = 0.0F;
            thiz.bipedHeadwear.rotationPointY = 0.0F;
            thiz.bipedHeadwear.rotateAngleX = thiz.bipedHead.rotateAngleX;
            thiz.bipedHeadwear.rotateAngleY = thiz.bipedHead.rotateAngleY;
            thiz.bipedHeadwear.rotateAngleZ = thiz.bipedHead.rotateAngleZ;
			thiz.bipedRightArm.rotateAngleX = 0f;
			thiz.bipedRightArm.rotateAngleY = 0f;
			thiz.bipedRightArm.rotateAngleZ = 0f;
			thiz.bipedLeftArm.rotateAngleX = 0f;
			thiz.bipedLeftArm.rotateAngleY = 0f;
			thiz.bipedLeftArm.rotateAngleZ = 0f;
			thiz.bipedRightLeg.rotateAngleX = (float) (Math.PI*-0.5);
			thiz.bipedRightLeg.rotateAngleY = 0f;
			thiz.bipedRightLeg.rotateAngleZ = 0f;
			thiz.bipedLeftLeg.rotateAngleX = (float) (Math.PI*-0.5);
			thiz.bipedLeftLeg.rotateAngleY = 0f;
			thiz.bipedLeftLeg.rotateAngleZ = 0f;
			
		}
        return;
    }

	
	
    public static void setAngles(Entity thiz, float p_70082_1_, float p_70082_2_)
    {
    	float f2 = thiz.rotationPitch;
        float f3 = thiz.rotationYaw;
        thiz.rotationYaw = (float)((double)thiz.rotationYaw + (double)p_70082_1_ * 0.15D);
        thiz.rotationPitch = (float)((double)thiz.rotationPitch - (double)p_70082_2_ * 0.15D);
        if(!(thiz.ridingEntity instanceof EntityBlackMamba)){
	        if (thiz.rotationPitch < -90.0F)
	        {
	            thiz.rotationPitch = -90.0F;
	        }
	
	        if (thiz.rotationPitch > 90.0F)
	        {
	            thiz.rotationPitch = 90.0F;
	        }
        }else{
//        	System.out.println(thiz.rotationYaw%360f);
//        	System.out.println(-thiz.ridingEntity.rotationYaw%360f);
        	float ridingYaw = -thiz.ridingEntity.rotationYaw%360f;
        	if(((EntityBlackMamba) thiz.ridingEntity).isOverHead()){
        		ridingYaw=(ridingYaw-180f)%360f;
        	}
        	if(thiz.rotationYaw%360 > (ridingYaw)+90F){
        		thiz.rotationYaw=((ridingYaw)+90F)%360f;
        	}
        	if(thiz.rotationYaw%360 < (ridingYaw)-90F){
        		thiz.rotationYaw=((ridingYaw)-90F)%360f;
        	}
        }
        thiz.prevRotationPitch += thiz.rotationPitch - f2;
        thiz.prevRotationYaw += thiz.rotationYaw - f3;
    }
    
    public static void mouseXYChange(MouseHelper thiz){;
    	thiz.deltaX = Mouse.getDX()*((Minecraft.getMinecraft().thePlayer.ridingEntity!=null&&Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityBlackMamba&&((EntityBlackMamba) Minecraft.getMinecraft().thePlayer.ridingEntity).isOverHead())?-1:1);
        thiz.deltaY = Mouse.getDY();
    }
	
}
