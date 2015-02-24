package ph.url.phlinthetime.mod.entity;

import java.lang.reflect.Method;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.asm.MethodInvokerClass;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityBlackMamba extends Entity {

	private AxisAlignedBB aabb;
	
	@Override
	public boolean interactFirst(EntityPlayer p) {
		if(this.worldObj.isRemote)return false;
		if(p.inventory.getCurrentItem() != null&&p.inventory.getCurrentItem().getItem().equals(ModPhantasialand.itemRotator)){
			this.setEntitySpeed(this.getEntitySpeed()+0.1F);
		}else{
			if(this.riddenByEntity == null){
				p.mountEntity(this);
			}
		}
		return true;
	}
		
	public EntityBlackMamba(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(2.5F, 2.75F);
		
	}
	
	public void setEntitySpeed(float speed){
		this.dataWatcher.updateObject(2, Float.valueOf(speed));
	}
	
	/**
	 * 
	 * @return the speed of the Train, in Blocks per second
	 */
	public float getEntitySpeed(){
		return this.dataWatcher.getWatchableObjectFloat(2);
	}
	
	public void setEntityWayOnBlockDone(float way){
		this.dataWatcher.updateObject(3, Float.valueOf(way));
	}
	
	public float getEntityWayOnBlockDone(){
		return this.dataWatcher.getWatchableObjectFloat(3);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(2, Float.valueOf(0));
		this.dataWatcher.addObject(3, Float.valueOf(0));
		this.dataWatcher.addObject(4, Integer.valueOf(0));
		this.dataWatcher.addObject(5, Integer.valueOf(0));
		this.dataWatcher.addObject(6, Integer.valueOf(0));
	}
	
	public void setBlockTrackEntityHangs(int x, int y, int z){
		if(this.worldObj == null)return;
		if(worldObj.getTileEntity(x, y, z)instanceof TileEntityTrack){
			this.dataWatcher.updateObject(4, Integer.valueOf(x));
			this.dataWatcher.updateObject(5, Integer.valueOf(y));
			this.dataWatcher.updateObject(6, Integer.valueOf(z));
		}else if(!(worldObj.getTileEntity(dataWatcher.getWatchableObjectInt(4), dataWatcher.getWatchableObjectInt(5), dataWatcher.getWatchableObjectInt(6)) instanceof TileEntityTrack)){
			this.dataWatcher.updateObject(4, Integer.valueOf(0));
			this.dataWatcher.updateObject(5, Integer.valueOf(0));
			this.dataWatcher.updateObject(6, Integer.valueOf(0));
		}
	}

	public int getHangingX() {
		return this.dataWatcher.getWatchableObjectInt(4);
	}
	

	public int getHangingY() {
		return this.dataWatcher.getWatchableObjectInt(5);
	}
	

	public int getHangingZ() {
		return this.dataWatcher.getWatchableObjectInt(6);
	}
	
	@Override
	public boolean canAttackWithItem() {
		return true;
	}
	
	@Override
	public double getMountedYOffset() {
		float f = (1f/16f)*-2f;
		if(isOverHead()){
			f -= 0.5F;
		}
		return 0.5F+f;
	}
	
	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(ModPhantasialand.itemBlackMamba, 1, 0);
	}
	
	private void printStackTrace(){
		System.out.println(this.worldObj==null?"null":this.worldObj.isRemote);
		try{
			throw new Exception("StackTraceException");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private int     toUpdateBlockPosX = 0	 ;
	private int     toUpdateBlockPosY = 0	 ;
	private int     toUpdateBlockPosZ = 0	 ;
	private float   toUpdateSpeed     = 0F	 ;
	private float   toUpdateWayDone   = 0F	 ;
	private boolean updateFromValues  = false;
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		toUpdateBlockPosX = nbt.getInteger("blockPosX");
		toUpdateBlockPosY = nbt.getInteger("blockPosY");
		toUpdateBlockPosZ = nbt.getInteger("blockPosZ");
		toUpdateSpeed = nbt.getFloat("speed");
		toUpdateWayDone = nbt.getFloat("wayDone");
		updateFromValues = true;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("blockPosX", this.getHangingX());
		nbt.setInteger("blockPosY", this.getHangingY());
		nbt.setInteger("blockPosZ", this.getHangingZ());
		nbt.setFloat("speed", this.getEntitySpeed());
		nbt.setFloat("wayDone", this.getEntityWayOnBlockDone());
	}	
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float f) {
		if(this.worldObj.isRemote)return false;
		if(source instanceof EntityDamageSource&&((EntityDamageSource)source).getEntity() instanceof EntityPlayer){
			EntityPlayer p = (EntityPlayer) ((EntityDamageSource)source).getEntity();
			if(p.inventory.getCurrentItem()!=null&&p.inventory.getCurrentItem().getItem().equals(ModPhantasialand.itemRotator)){
				this.setDead();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isEntityInvulnerable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isOverHead(){
		return this.rotationPitch%360 >= 180;
	}
	
	@Override
	public void onEntityUpdate() {
		this.worldObj.theProfiler.startSection("entityBaseTick");
		if(updateFromValues){
			updateFromValues = false;
			this.setBlockTrackEntityHangs(this.toUpdateBlockPosX, this.toUpdateBlockPosY, this.toUpdateBlockPosZ);;
			this.setEntitySpeed(this.toUpdateSpeed);
			this.setEntityWayOnBlockDone(this.toUpdateWayDone);
		}
		if (this.ridingEntity != null && this.ridingEntity.isDead)
        {
            this.ridingEntity = null;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
		TileEntity te_ = worldObj.getTileEntity(this.getHangingX(), this.getHangingY(), this.getHangingZ());
		if(te_ != null&&te_ instanceof TileEntityTrack){
			TileEntityTrack te = (TileEntityTrack) te_;
			te.calculatePositionRotationAndUpdate(this, true);
			this.updateRiderPosition();
		}
		this.worldObj.theProfiler.endSection();
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	private float lastUpdateRotation = 0F;
	private float lastUpdateRotationP = 0F;
	
	@Override
	public void updateRiderPosition() {
		 if (this.riddenByEntity != null)
	        {
	            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
	    		this.riddenByEntity.lastTickPosX = this.riddenByEntity.posX;
	    		this.riddenByEntity.lastTickPosY = this.riddenByEntity.posY;
	    		this.riddenByEntity.lastTickPosZ = this.riddenByEntity.posZ;
	    		
	    		float f = -this.rotationYaw+this.lastUpdateRotation;
	    		f += this.riddenByEntity.rotationYaw;
	    		f = f % 360;
	    		this.riddenByEntity.rotationYaw = f;
	    		this.riddenByEntity.prevRotationYaw = f;
	    		
	    		f = this.rotationPitch-this.lastUpdateRotationP;
	    		if(f >= 180F||f <= -180F){
		    		this.riddenByEntity.rotationYaw += 180f;;
		    		this.riddenByEntity.prevRotationYaw += 180f;
	    		}
	    		f += this.riddenByEntity.rotationPitch;
	    		f = f % 360;
	    		this.riddenByEntity.rotationPitch = f;
	    		this.riddenByEntity.prevRotationPitch = f;
	    		
	        }
		 lastUpdateRotation = this.rotationYaw;
		 lastUpdateRotationP = this.rotationPitch;
	}

	public void setRotation(float i) {
		this.rotationYaw = i%360;
		
	}
	
	

	private boolean renderUpdateDone;
	
	public void markRenderUpdateDone() {
		renderUpdateDone = true;
	}
	
	public boolean isRenderUpdateDone(){
		return renderUpdateDone;
	}
	
	public void setWasRendered(){
		renderUpdateDone = false;
	}

	
	

}
