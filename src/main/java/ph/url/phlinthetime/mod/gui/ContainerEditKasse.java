package ph.url.phlinthetime.mod.gui;

import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerEditKasse extends Container {

	private InventoryPlayer invPlayer;
	private TileEntityKasse invKasse;
	private int[] prices;

	public ContainerEditKasse(InventoryPlayer inventory, TileEntityKasse tileEntity) {
		invPlayer = inventory;
		invKasse = tileEntity;
		prices = new int[invKasse.getSizeInventory()];
		addSlots();
		detectAndSendChanges();
		invKasse.isBeingEdited = true;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		invKasse.isBeingEdited = false;
	}
	
	public void setItemPrice(int isnr, int price) {
		if(isnr <= invKasse.getSizeInventory()){
			invKasse.setItemPrice(price, isnr);
			detectAndSendChanges();
		}
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
	}
	
	@Override
	public void updateProgressBar(int id, int value) {
		if(id>=32){
			id=id-32;
			if(id<invKasse.getSizeInventory()){
				prices[id]=value;
			this.invKasse.setItemPrice(value, id);}
		}
	}
	
	private void addSlots() {
		int x = 131;
		int y = 17;
		int y2 = 22;
			this.addSlotToContainer(new Slot(invKasse, 0, x, y         ));
			this.addSlotToContainer(new Slot(invKasse, 1, x, y+y2      ));
			this.addSlotToContainer(new Slot(invKasse, 2, x, y+y2+y2   ));
			this.addSlotToContainer(new Slot(invKasse, 3, x, y+y2+y2+y2));
			
		
		
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
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return invKasse.isUseableByPlayer(var1);
	}

	public String getPrice(int i) {
		// TODO Auto-generated method stub
		return prices[i]+"";
	}

}
