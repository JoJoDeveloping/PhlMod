package ph.url.phlinthetime.mod.gui;

import ph.url.phlinthetime.mod.money.MoneyManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotSell extends Slot {

	protected ContainerKasse kasse;
	
	public SlotSell(IInventory par1iInventory, int par2, int par3, int par4, ContainerKasse kasse) {
		super(par1iInventory, par2, par3, par4);
		this.kasse = kasse;
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return isEditable;
	}
	
	@Override
	public ItemStack decrStackSize(int par1) {
		// TODO Auto-generated method stub
		return kasse.invPlayer.getItemStack()==null?new ItemStack(((Item)null), 0, 0):kasse.invPlayer.getItemStack();
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
		if(!kasse.invKasse.getWorldObj().isRemote)
		this.onCrafting(par2ItemStack);
		
		this.putStack(par2ItemStack);
		kasse.invPlayer.setItemStack(null);
	}
	
	@Override
	public void putStack(ItemStack par1ItemStack) {
		if(isEditable)this.inventory.setInventorySlotContents(getSlotIndex(), par1ItemStack);
	}
	
	@Override
	protected void onCrafting(ItemStack is) {
		if(MoneyManager.instance!=null)
		kasse.doBuy(this.getSlotIndex());
	}

	private boolean isEditable = false;
	
	public void setEditable(boolean b) {
		this.isEditable = b;
	}

}
