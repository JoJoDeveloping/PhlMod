package ph.url.phlinthetime.mod.block;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockKasse extends BlockContainer {

	public BlockKasse() {
		super(Material.ground);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		// TODO Auto-generated method stub
		return -1;
	}
	
	@Override
	public boolean getUseNeighborBrightness() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
		switch(world.getBlockMetadata(x, y, z)){
		case 0:
			this.setBlockBounds(0.0625F*1F, 0F, 0.0625F*3F, 0.0625F*(16F-1F), 0.0625F*(16F-2F), 0.0625F*(16F-2F));
			break;
		case 3:
			this.setBlockBounds(0.0625F*3F, 0F, 0.0625F*1F, 0.0625F*(16F-2F), 0.0625F*(16F-2F), 0.0625F*(16F-1F));
			break;
		case 2:
			this.setBlockBounds(0.0625F*1F, 0F, 0.0625F*2F, 0.0625F*(16F-1F), 0.0625F*(16F-2F), 0.0625F*(16F-3F));
			break;
		case 1:
			this.setBlockBounds(0.0625F*2F, 0F, 0.0625F*1F, 0.0625F*(16F-3F), 0.0625F*(16F-2F), 0.0625F*(16F-1F));
			break;
		}
	}
	
	@Override
	public int getRenderBlockPass() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	 public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
	    {
	        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	       
	            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);
	    }
	
	
	 
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float f, float f2, float f3) {
		TileEntity te_ = world.getTileEntity(x, y, z);
		if(player.isSneaking()||te_==null||!(te_ instanceof TileEntityKasse)) return false;
		TileEntityKasse te = (TileEntityKasse) te_;
		if((player.inventory.getCurrentItem()!=null&&player.inventory.getCurrentItem().getItem()==ModPhantasialand.itemRotator)){
			player.openGui(ModPhantasialand.INSTANCE, 1, world, x, y, z);
		}else{
			player.openGui(ModPhantasialand.INSTANCE, 0, world, x, y, z);
		}
		return true;
	}
	 
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileEntityKasse(var1);
	}
	
	@Override
	public boolean isNormalCube() {
		return false;
	}

}
