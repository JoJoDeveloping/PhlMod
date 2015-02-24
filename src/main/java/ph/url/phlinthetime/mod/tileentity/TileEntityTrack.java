package ph.url.phlinthetime.mod.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.Values;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import ph.url.phlinthetime.mod.networking.packets.MainPacket;

public class TileEntityTrack extends TileEntity {


	
	public TileEntityTrack(){
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		// TODO Auto-generated method stub
		return 4096D*1D;
	}
	
	private int rotY = 0;
	private int steigung = 0;
	
	public void setSteigungAndRotaton(int steigung, int rotY){ 
		this.steigung = steigung;
		if(this.steigung > 7)
			this.steigung = 0;
		
		this.rotY = rotY;
		if(this.rotY > 7)
			this.rotY = 0;
			
	}	
	public void setSteigungAndRotaton2(int steigung, int rotY){ 
		this.steigung = steigung;
		if(this.steigung > 7)
			this.steigung = 0;
		
		this.rotY = rotY;
		if(this.rotY > 7)
			this.rotY = 0;
			
	}
	
	int nextUpdate = 0;
	
	private boolean hasBeenUpdatet = false;
	
	@Override
	public void updateEntity(){
		if(worldObj.isRemote){
			if(this.rotY == 0 && this.steigung == 0 && !hasBeenUpdatet){
				hasBeenUpdatet = true;
				try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos);
				dos.writeByte(Values.PACKET_TRAIN_UPDTAE);
				dos.writeByte(14);
				dos.writeInt(this.xCoord);
				dos.writeInt(this.yCoord);
				dos.writeInt(this.zCoord);
				ModPhantasialand.networkCon.sendToServer(new MainPacket(baos.toByteArray(), ModPhantasialand.MODID));
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void onChunkUnload() {
		if(worldObj.isRemote){
			hasBeenUpdatet = false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		super.readFromNBT(nbt);
		if(nbt.hasKey("rotY"))
		rotY = nbt.getInteger("rotY");
		if(nbt.hasKey("steigung"))
		steigung = nbt.getInteger("steigung");
		if(this.worldObj!=null)
		getNextTrackTE(false).getNextTrackTE(true);

	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		super.writeToNBT(nbt);
		nbt.setInteger("rotY", rotY);
		nbt.setInteger("steigung", steigung);
	}
	
	public int getSteigung(){
		return steigung;
	}
	public int getRotY(){
		return rotY;
	}
	private TileEntityTrack getNextTrackTE() {
		return getNextTrackTE(true);
	}

	private boolean mustTrainTakeOtherWay = false;

	public TileEntityTrack getNextTrackTE(boolean b) {
		TileEntity result = null;
		if(mustTrainTakeOtherWay){
			mustTrainTakeOtherWay = true;
			switch(this.getRotY()){
			case 0: 
			case 7: 
				result = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord-1);
				break;
			case 1: 
			case 2: 
				result = this.worldObj.getTileEntity(this.xCoord-1, this.yCoord, this.zCoord);
				break;
			case 3: 
			case 4: 
				result = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord+1);
				break;
			case 5: 
			case 6: 
				result = this.worldObj.getTileEntity(this.xCoord+1, this.yCoord, this.zCoord);
				break;
			}
		}else{
			switch(this.getRotY()){
			case 0: 
			case 1: 
				result = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord+1);
				break;
			case 2: 
			case 3: 
				result = this.worldObj.getTileEntity(this.xCoord+1, this.yCoord, this.zCoord);
				break;
			case 4: 
			case 5: 
				result = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord-1);
				break;
			case 6: 
			case 7: 
				result = this.worldObj.getTileEntity(this.xCoord-1, this.yCoord, this.zCoord);
				break;
			}
		}
		if(result == null)return null;
		if(result instanceof TileEntityTrack){
			((TileEntityTrack) result).mustTrainTakeOtherWay = false;
			if(b&&((((TileEntityTrack) result).rotY&1)==1)&&((TileEntityTrack) result).getNextTrackTE(false)!=null&&((TileEntityTrack) result).getNextTrackTE(false).equals(this)){
				((TileEntityTrack) result).mustTrainTakeOtherWay = true;
			}
			return (TileEntityTrack) result;
		}
		
		return null;
	}
	private float getMaxWay() {
		if((this.rotY&1)==0){
			return 1F;
		}else{
			return (float)(Math.PI / 4);
		}		
	}
	public void calculatePositionRotationAndUpdate(EntityBlackMamba ebm, boolean applySpeed, float additional, boolean updateTrain) {
		if(ebm.worldObj != null && ebm.worldObj.equals(this.worldObj) && this.worldObj.getTileEntity(ebm.getHangingX(), ebm.getHangingY(), ebm.getHangingZ())==this){
			float speedToApply = (applySpeed?(ebm.getEntitySpeed()/20F):0F);
			float wayDone = ebm.getEntityWayOnBlockDone() +speedToApply;
			wayDone += additional;
			if(updateTrain)ebm.setEntityWayOnBlockDone(wayDone);
			if(wayDone >= this.getMaxWay()){
				TileEntityTrack next = getNextTrackTE();
				if(next == null)return;
				if(updateTrain){
					ebm.setEntityWayOnBlockDone(wayDone % this.getMaxWay());
					ebm.setBlockTrackEntityHangs(next.xCoord, next.yCoord, next.zCoord);
					next.calculatePositionRotationAndUpdate(ebm, false, 0F, updateTrain);
				}
				if(!updateTrain){
					float wayDoneForNext = wayDone - this.getMaxWay();
					next.calculatePositionRotationAndUpdate(ebm, false, wayDoneForNext-ebm.getEntityWayOnBlockDone(), updateTrain);					
				}
				if(mustTrainTakeOtherWay)
					wayDone = ebm.getEntityWayOnBlockDone()-speedToApply;
			}
			else if(this.getSteigung() == 0||this.steigung==4){
				wayDone = wayDone - this.getMaxWay();
				if(!mustTrainTakeOtherWay)
					switch(this.getRotY()){
					case 0:
						ebm.setPosition(this.xCoord+0.5F, this.yCoord-2.5F, this.zCoord+1F+wayDone);
						ebm.setRotation(0F);
						break;
					case 2:
						ebm.setPosition(this.xCoord+1F+wayDone, this.yCoord-2.5F, this.zCoord+0.5F);
						ebm.setRotation(90F);
						break;
					case 4:
						ebm.setPosition(this.xCoord+0.5F, this.yCoord-2.5F, this.zCoord-wayDone);
						ebm.setRotation(180F);
						break;
					case 6:
						ebm.setPosition(this.xCoord-wayDone, this.yCoord-2.5F, this.zCoord+0.5F);
						ebm.setRotation(270F);
						break;
					case 1:
						float angle = (wayDone/this.getMaxWay())*90F;
						float radius = 0.5F;
						float centerX = 0F;
						float centerZ = 1F;
						float xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    float zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle);
						break;
					case 3:
						angle = (wayDone/this.getMaxWay())*90F+270F;
						radius = 0.5F;
						centerX = 1F;
						centerZ = 1F;
						xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle);
						break;
					case 5:
						angle = (wayDone/this.getMaxWay())*90F+180F;
						radius = 0.5F;
						centerX = 1F;
						centerZ = 0F;
						xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle);
						break;
					case 7:
						angle = (wayDone/this.getMaxWay())*90F+90F;
						radius = 0.5F;
						centerX = 0F;
						centerZ = 0F;
						xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle);
						break;
					}
				if(mustTrainTakeOtherWay){
					float wayOld = wayDone;
					wayDone = -(getMaxWay()+wayDone);
					switch(this.getRotY()){
					case 1:
						float angle = (wayDone/this.getMaxWay())*90F;
						float radius = 0.5F;
						float centerX = 0F;
						float centerZ = 1F;
						float xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    float zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle+90F);
						break;
					case 3:
						angle = (wayDone/this.getMaxWay())*90F+270F;
						radius = 0.5F;
						centerX = 1F;
						centerZ = 1F;
						xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle+90F);
						break;
					case 5:
						angle = (wayDone/this.getMaxWay())*90F+180F;
						radius = 0.5F;
						centerX = 1F;
						centerZ = 0F;
						xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle+90);
						break;
					case 7:
						angle = (wayDone/this.getMaxWay())*90F+90F;
						radius = 0.5F;
						centerX = 0F;
						centerZ = 0F;
						xPos = radius * (float)Math.cos(Math.toRadians(angle)) + centerX;
					    zPos = radius * (float)Math.sin(Math.toRadians(angle)) + centerZ;
						ebm.setPosition(this.xCoord+xPos, this.yCoord-2.5F, this.zCoord+zPos);
						ebm.setRotation(-angle+90F);
						break;
					}
					ebm.setRotation(ebm.rotationYaw+90F);
				}
				if(this.steigung==4){
					ebm.rotationPitch=180f;
					ebm.setPosition(ebm.posX, ebm.posY+ebm.height+0.5f, ebm.posZ);
				}else{
					ebm.rotationPitch=0f;
				}
			}
		}
		ebm.prevPosX=ebm.posX;
		ebm.prevPosY=ebm.posY;
		ebm.prevPosZ=ebm.posZ;
		ebm.prevRotationPitch=ebm.rotationPitch;
		ebm.prevRotationYaw=ebm.rotationYaw;
		ebm.lastTickPosX=ebm.posX;
		ebm.lastTickPosY=ebm.posY;
		ebm.lastTickPosZ=ebm.posZ;
	}
	
	public void calculatePositionRotationAndUpdate(EntityBlackMamba ebm, boolean applySpeed){
		this.calculatePositionRotationAndUpdate(ebm, applySpeed, 0F, true);
	}
	
	
}
