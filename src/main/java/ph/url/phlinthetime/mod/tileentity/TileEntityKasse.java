package ph.url.phlinthetime.mod.tileentity;

import java.util.Random;

import ph.url.phlinthetime.mod.money.MoneyManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ph.url.phlinthetime.mod.money.PlayerInfo;

public class TileEntityKasse extends TileEntity implements IInventory {

	public TileEntityKasse(World w) {
		super();
		this.setWorldObj(w);
	}

	public TileEntityKasse() {
	}

	@Override
	public int getBlockMetadata() {
		if (this.worldObj == null)
			return 1;

		return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	}

	private ItemStack[] slots = new ItemStack[27];
	private int[] prices = new int[27];
	private String customName = null;

	@Override
	public int getSizeInventory() {
		return 27;
	}
	

	public void setItemPrice(int price, int itemSlotNr) {
			prices[itemSlotNr] = price;
	}

	public int getItemPrice(int itemSlotNr) {
		return prices[itemSlotNr];
	}

	public boolean canBuy(String uuid, int itemSlotNr) {
		return (MoneyManager.instance != null
				&& MoneyManager.instance.hashmap.containsKey(uuid)
				&& getItemPrice(itemSlotNr) != 0 && slots[itemSlotNr] != null && ((((PlayerInfo) (MoneyManager.instance.hashmap
				.get(uuid))).getMoney()) >= getItemPrice(itemSlotNr)));
	}

	public void buy(String uuid, int itemSlotNr) {
		if (!canBuy(uuid, itemSlotNr))
			return;
		int i =getItemPrice(itemSlotNr);
		MoneyManager.instance.hashmap.get(uuid).setMoney(
				MoneyManager.instance.hashmap.get(uuid).getMoney()
						- i);
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.slots[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.slots[par1] != null) {
			ItemStack itemstack;

			if (this.slots[par1].stackSize <= par2) {
				itemstack = this.slots[par1];
				this.slots[par1] = null;
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.slots[par1].splitStack(par2);

				if (this.slots[par1].stackSize == 0) {
					this.slots[par1] = null;
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return new ItemStack(((Item)(null)) , 0, 0);
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		return slots[par1];
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.slots[par1] = par2ItemStack;

		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "Kasse";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return isBeingEdited&&this.worldObj.getTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	public boolean isBeingEdited = false;
	
	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.slots.length; ++i) {
			if (this.slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("slot", (byte) i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttagcompound1.setInteger("price", getItemPrice(i));
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		p_145841_1_.setTag("Items", nbttaglist);

		if (this.hasCustomInventoryName()) {
			p_145841_1_.setString("CustomName", this.customName);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];
		this.prices = new int[this.getSizeInventory()];

		if (p_145839_1_.hasKey("CustomName", 8)) {
			this.customName = p_145839_1_.getString("CustomName");
		}

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("slot") & 255;

			if (j >= 0 && j < this.slots.length) {
				this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				this.prices[j] = nbttagcompound1.getInteger("price");
				}
		}
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		return true;
	}


}
