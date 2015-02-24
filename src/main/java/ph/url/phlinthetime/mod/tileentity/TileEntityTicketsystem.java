package ph.url.phlinthetime.mod.tileentity;

import ph.url.phlinthetime.mod.block.BlockTicketsystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class TileEntityTicketsystem extends TileEntity implements IInvBasic {

    public InventoryBasic internalInventory;
	
	private int redstoneOnDelay = 0;
	
	public TileEntityTicketsystem(World w) {
		super();
		internalInventory = new InventoryBasic("", false, 2);
		internalInventory.func_110134_a(this);
		this.setWorldObj(w);
	}
	
	public TileEntityTicketsystem() {
		this(null);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.internalInventory.getSizeInventory(); ++i)
        {
            if (this.internalInventory.getStackInSlot(i) != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.internalInventory.getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbt.setTag("Items", nbttaglist);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.internalInventory = new InventoryBasic("", false, 2);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.internalInventory.getSizeInventory())
            {
                this.internalInventory.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
		internalInventory.func_110134_a(this);
	}
	
	@Override
	public int getBlockMetadata() {
		if (worldObj != null){
			return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	    }
		return 1;
	}
	
	public ItemStack checkTicketAndOpenIfGiven(ItemStack is, EntityPlayer player){
		if(is!=null&&this.internalInventory.getStackInSlot(0)!=null&&ItemStack.areItemStacksEqual(is, this.internalInventory.getStackInSlot(0))){
			BlockTicketsystem.changeOpenMode(this.worldObj, this.xCoord, this.yCoord, this.zCoord, true);
			if(this.internalInventory.getStackInSlot(1)==null)return null;
			return this.internalInventory.getStackInSlot(1).copy();
		}
		
		return is;
	}
	
	@Override
	public void updateEntity() {
		if(this.worldObj.isRemote)return;
		if(this.redstoneOnDelay > 0){
			redstoneOnDelay--;
			if(redstoneOnDelay == 0){
				BlockTicketsystem.changeOpenMode(getWorldObj(), xCoord, yCoord, zCoord, false);
			}
		}
	}

	public void setRedstoneOnDelay(int i) {
		this.redstoneOnDelay = i;
	}

	@Override
	public void onInventoryChanged(InventoryBasic var1) {
		this.markDirty();		
	}

	

}
