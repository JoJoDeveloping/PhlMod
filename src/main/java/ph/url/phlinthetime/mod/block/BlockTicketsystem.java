package ph.url.phlinthetime.mod.block;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.Values;
import ph.url.phlinthetime.mod.networking.packets.MainPacket;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTicketsystem extends BlockContainer {

	public BlockTicketsystem() {
		super(Material.ground);
		this.setBlockTextureName("stone");
	}
	
	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	public static void changeOpenMode(World world, int x, int y, int z, boolean newState){
		int meta = world.getBlockMetadata(x, y, z);
		int newMeta = (meta&12)+(newState?1:0);
		TileEntityTicketsystem sys = (TileEntityTicketsystem) world.getTileEntity(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
		if(newState){sys.setRedstoneOnDelay(ModPhantasialand.config.ticketsystemRedstoneDelay());}
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x+1, y, z));
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y+1, z));
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z+1));
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x-1, y, z));
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y-1, z));
		world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z-1));
	}
	
	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
		return isProvidingWeakPower(world, x, y, z, side);
	}
	
	private int getPowerOut(int meta, int side) {
		if((meta&1)==0){
			return 0;
		}
		if(side==5&&meta==9)return 15;
		if(side==3&&meta==13)return 15;
		if(side==2&&meta==5)return 15;
		if(side==4&&meta==1)return 15;
		return 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		return getPowerOut(meta, side);
	}

	private boolean smallCubeMopPass = false;
	private boolean BBMopPass = false;
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTicketsystem(var1);
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase player, ItemStack is) {
		int rot = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		w.setBlockMetadataWithNotify(x, y, z, rot<<2, 3);
	}

	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean canProvidePower() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess w, int x, int y, int z, ForgeDirection side) {
		return false;
	}
	
	
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int dide, float hitX, float hitY, float hitZ) {
		TileEntity te_ = w.getTileEntity(x, y, z);
		if(player.isSneaking()||te_==null||!(te_ instanceof TileEntityTicketsystem)) return false;
		TileEntityTicketsystem te = (TileEntityTicketsystem) te_;
		if((player.inventory.getCurrentItem()!=null&&player.inventory.getCurrentItem().getItem()==ModPhantasialand.itemRotator)){
			player.openGui(ModPhantasialand.INSTANCE, 2, w, x, y, z);
		}
		if(player.inventory.getCurrentItem()!=null&&player.inventory.getCurrentItem().getItem()!=ModPhantasialand.itemRotator&&hitY > 0.875F){
			if(w.isRemote){
				
				this.BBMopPass = true;
				
				Vec3 vec3 = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
				Vec3 vec31 = player.getLook(1F);
		        Vec3 vec32 = vec3.addVector(vec31.xCoord * 4.5F, vec31.yCoord * 4.5F, vec31.zCoord * 4.5F);
		        w.func_147447_a(vec3, vec32, false, false, true);
		        
		        if(minY == 0.875){
		        	
		        	//player.inventory.setInventorySlotContents(player.inventory.currentItem, te.checkTicketAndOpenIfGiven(player.inventory.getCurrentItem(), player));
					try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream dos = new DataOutputStream(baos);
					dos.writeByte(Values.PACKET_TICKETSYSTEM_CLICK);
					dos.writeByte(-1);
					dos.writeInt(x);
					dos.writeInt(y);
					dos.writeInt(z);
					dos.writeUTF(player.getUniqueID().toString());
					dos.close();				
					ModPhantasialand.networkCon.sendToServer(new MainPacket(baos.toByteArray(), ModPhantasialand.MODID));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        	
		        }
				
				this.BBMopPass = false;
				vec3 = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
				vec31 = player.getLook(1F);
		        vec32 = vec3.addVector(vec31.xCoord * 4.5F, vec31.yCoord * 4.5F, vec31.zCoord * 4.5F);
		        w.func_147447_a(vec3, vec32, false, false, true);
				
				
			}
		}
		
		
//		
//        
		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess w, int x, int y, int z) {
		if(!BBMopPass){
			this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
			return;
		}
		int meta = (w.getBlockMetadata(x, y, z) & 14) >> 2;
		if(smallCubeMopPass){
			switch (meta){
			case 0:
				this.setBlockBounds((1F/16F*12F), 0.875F, (1F/16F*5F), (1F/16F*15F), (1F), (1F/16F*8F));
				break;
			case 1:
				this.setBlockBounds((1F/16F*8F), 0.875F, (1F/16F*12F), (1F/16F*11F), (1F), (1F/16F*15F));
				break;
			case 2:
				this.setBlockBounds((1F/16F*1F), 0.875F, (1F/16F*8F), (1F/16F*4F), (1F), (1F/16F*11F));
				break;
			case 3:
				this.setBlockBounds((1F/16F*5F), 0.875F, (1F/16F*1F), (1F/16F*8F), (1F), (1F/16F*4F));
				break;
			default:
				this.setBlockBounds((1F/16F*7F), 0.875F, (1F/16F*7F), (1F/16F*9F), (1F), (1F/16F*9F));
				System.out.println("Strange metadata "+meta);
			}
		}else if(!smallCubeMopPass){
			this.setBlockBounds(0F, 0F, 0F, 1F, 0.875F, 1F);
		}
	}
	
	
	
	@Override
	public MovingObjectPosition collisionRayTrace(World w, int x2, int y2, int z2, Vec3 vec1, Vec3 vec2) {
		double x = (vec1.xCoord-x2);
		double y = (vec1.yCoord-y2);
		double z = (vec1.zCoord-z2);
		this.smallCubeMopPass = false;
		if(y>=0.875D){
			this.smallCubeMopPass = true;
		}
		MovingObjectPosition mop = super.collisionRayTrace(w, x2, y2, z2, vec1, vec2);
		if(mop==null){
			this.smallCubeMopPass = !smallCubeMopPass;
			mop = super.collisionRayTrace(w, x2, y2, z2, vec1, vec2);
		}
		//this.smallCubeMopPass = false;
		//this.setBlockBoundsBasedOnState(w, x2, y2, z2);
		return mop;
	}
	
	public void setSmallCubeBbRequested(boolean b) {
		smallCubeMopPass = b;
		
	}

	public void setBBRaytracing(boolean b) {
		BBMopPass = b;
	}
	

    
	
}
