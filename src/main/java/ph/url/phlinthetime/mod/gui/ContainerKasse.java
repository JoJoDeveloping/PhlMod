package ph.url.phlinthetime.mod.gui;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerKasse extends Container implements IInvBasic{

	public InventoryPlayer invPlayer;
	public TileEntityKasse invKasse;
	public IInventory invOutput;
	public IInventory invCC;
	
	
	/**
	 * Do <b>NOT</b> set this manually!!!
	 */
	
	protected int[] prices;
	
	public ContainerKasse(InventoryPlayer invP, TileEntityKasse te_) {
		
		invPlayer = invP;
		invKasse = te_;
		invOutput = new InventoryBasic("", false, 3);
		invCC = new InventoryBasic("", false, 1);
		((InventoryBasic) invOutput).func_110134_a(this);
		prices = new int[invKasse.getSizeInventory()];
		addSlots();
	}

	public boolean mergeOutput(ItemStack is){
		is=is.copy();
		
		for(int i =0; i < 3; i++){
			if(this.invOutput.getStackInSlot(i)==null){
				this.invOutput.setInventorySlotContents(i, is);
				return true;
			}
			if(this.invOutput.getStackInSlot(i).isItemEqual(is)&&ItemStack.areItemStackTagsEqual(this.invOutput.getStackInSlot(0), is)&&(this.invOutput.getStackInSlot(0).stackSize+is.stackSize)<=is.getItem().getItemStackLimit(is)){
				ItemStack is2 = this.invOutput.getStackInSlot(i);
				is2.stackSize = is.stackSize+is2.stackSize;
				this.invOutput.setInventorySlotContents(i, is2);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canDragIntoSlot(Slot slot) {
	     return slot.inventory == invPlayer;
	}
	
	public void doBuy(int isnr){
			String uuid = "";
			ItemStack cc = this.invCC.getStackInSlot(0);
			if(cc!=null&&cc.getItem().equals(ModPhantasialand.itemCreditCard)&&cc.hasTagCompound()&&cc.stackTagCompound.hasKey("owner")){
				uuid=cc.stackTagCompound.getString("owner");
				if(invKasse.canBuy(uuid, isnr)&&mergeOutput(invKasse.getStackInSlot(isnr))){
					invKasse.buy(uuid, isnr);
					
				}
			}
			detectAndSendChanges();
			
	}
	
	public static boolean isValidCreditCard(ItemStack is){
		return is!=null&&is.getItem()!=null&&is.stackSize==1&&is.hasTagCompound()&&is.getItem().equals(ModPhantasialand.itemCreditCard)&&is.stackTagCompound.hasKey("owner");
	}
	
	private void addSlots() {
		int x = 131;
		int y = 17;
		int y2 = 22;
			this.addSlotToContainer(new SlotSell(invKasse, 0, x, y,          this));
			this.addSlotToContainer(new SlotSell(invKasse, 1, x, y+y2,       this));
			this.addSlotToContainer(new SlotSell(invKasse, 2, x, y+y2+y2,    this));
			this.addSlotToContainer(new SlotSell(invKasse, 3, x, y+y2+y2+y2, this));
			y = 50;
			this.addSlotToContainer(new Slot(invCC, 0, 13, y-36){
				@Override
				public boolean isItemValid(ItemStack par1ItemStack) {
					return isValidCreditCard(par1ItemStack);
				}
			});
			this.addSlotToContainer(new SlotFurnace(invPlayer.player, invOutput, 0, 13, y){
				@Override
				protected void onCrafting(ItemStack par1ItemStack) {
					
				}
			});
			this.addSlotToContainer(new SlotFurnace(invPlayer.player, invOutput, 1, 13, y+18){
				@Override
				protected void onCrafting(ItemStack par1ItemStack) {
					
				}
			});
			this.addSlotToContainer(new SlotFurnace(invPlayer.player, invOutput, 2, 13, y+36){
				@Override
				protected void onCrafting(ItemStack par1ItemStack) {
					
				}
			});
		
		
			for (int i = 0; i < 3; ++i)
	        {
	            for (int j = 0; j < 9; ++j)
	            {
	                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 118 + i * 18));
	            }
	        }

	        for (int i = 0; i < 9; ++i)
	        {
	            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 176));
	        }		
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNr){ 
		Slot slot = this.getSlot(slotNr);
		if(slot.inventory==invPlayer){
			if(isValidCreditCard(slot.getStack())){
			invCC.setInventorySlotContents(0, slot.getStack());
			slot.putStack(null);}
		}
		else if(slot.getStack()!=null&&(slot.inventory==invOutput||slot.inventory==invCC)){
			if(mergeItemStack(slot.getStack(), 8, 8+36, false))
				slot.putStack(null);
		}
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return invKasse.isUseableByPlayer(var1);
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
        if(invCC.getStackInSlot(0)!=null)par1EntityPlayer.dropPlayerItemWithRandomChoice(invCC.getStackInSlotOnClosing(0), false);
        if(invOutput.getStackInSlot(0)!=null)par1EntityPlayer.dropPlayerItemWithRandomChoice(invOutput.getStackInSlot(0), false);
        if(invOutput.getStackInSlot(1)!=null)par1EntityPlayer.dropPlayerItemWithRandomChoice(invOutput.getStackInSlot(1), false);
        if(invOutput.getStackInSlot(2)!=null)par1EntityPlayer.dropPlayerItemWithRandomChoice(invOutput.getStackInSlot(2), false);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < invKasse.getSizeInventory(); i++){
			if(invKasse.getItemPrice(i) != prices[i]){
			this.prices[i] = invKasse.getItemPrice(i);
			 for (int j = 0; j < this.crafters.size(); ++j)
                {
                    ((ICrafting)this.crafters.get(j)).sendProgressBarUpdate(this, i+32, invKasse.getItemPrice(i));
                }
			}
		}
		if(invKasse.isBeingEdited){
			for (int j = 0; j < this.crafters.size(); ++j)
            {
               if(crafters.get(j) instanceof EntityPlayerMP){
            	   EntityPlayerMP pl = (EntityPlayerMP) crafters.get(j);
            	   this.onContainerClosed(pl);
            	   pl.closeContainer();
               }
            }
		}
	}
	
	@Override
	public void updateProgressBar(int id, int value) {
		if(id>=32){
			id=id-32;
			if(id<invKasse.getSizeInventory()){
				prices[id]=value;
			this.invKasse.setItemPrice(value, id);}
		}
		if(id==2){
			boolean b = value==1?true:false;
			for(int i = 0; i < this.inventorySlots.size(); i++){
				Slot s = (Slot) this.inventorySlots.get(i);
				if(s.inventory == invKasse){
					((SlotSell)(s)).setEditable(b);
				}
			}
		}
	}
	
	@Override
	public void onInventoryChanged(InventoryBasic var1) {
		this.detectAndSendChanges();
	}

	public int getPrice(int i) {
		return prices[i];
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		par1iCrafting.sendProgressBarUpdate(this, 2, 1);
		super.addCraftingToCrafters(par1iCrafting);
		par1iCrafting.sendSlotContents(this, 0, this.getSlot(0).getStack());
		par1iCrafting.sendSlotContents(this, 1, this.getSlot(1).getStack());
		par1iCrafting.sendSlotContents(this, 2, this.getSlot(2).getStack());
		par1iCrafting.sendSlotContents(this, 3, this.getSlot(3).getStack());
		par1iCrafting.sendProgressBarUpdate(this, 2, 0);
	}
	
}