package ph.url.phlinthetime.mod.block;

import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockTrack extends BlockContainer {

	public BlockTrack() {
		super(Material.rock);
		this.setBlockTextureName("dirt");
		this.setCreativeTab(CreativeTabs.tabTransport);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(w.getTileEntity(x, y, z)!=null&&w.getTileEntity(x, y, z)instanceof TileEntityTrack){
			TileEntityTrack te = (TileEntityTrack) w.getTileEntity(x, y, z);
			/*player.addChatMessage(new ChatComponentText("Rotation: "+te.getRotY()));
			player.addChatMessage(new ChatComponentText("Steigung: "+te.getSteigung()));*/
		}
		return false;
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
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float ClickX, float ClickY, float ClickZ, int meta) {
		// TODO Auto-generated method stub
		
		
		return super.onBlockPlaced(world, x, y, z, side, ClickX, ClickY, ClickZ, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileEntityTrack();
	}

}
