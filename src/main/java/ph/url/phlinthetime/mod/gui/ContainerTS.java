package ph.url.phlinthetime.mod.gui;

import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerTS extends Container {

	private InventoryPlayer invPlayer;
	private TileEntityTicketsystem sysTicket;
	private IInventory invTicket;
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}
	
	public ContainerTS(InventoryPlayer invP, TileEntityTicketsystem invTS){
		invPlayer = invP;
		sysTicket = invTS;
		invTicket = sysTicket.internalInventory;
		addSlots();
	}

	
	
	private void addSlots() {
		for(int i = 0; i < 9; i++){
			this.addSlotToContainer(new Slot(invPlayer, i, 8+(18*i), 142));
		}
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 9; x++){
				this.addSlotToContainer(new Slot(invPlayer, (y+1)*9+x, 8+(18*x), 84+(18*y)));
			}
		}

		this.addSlotToContainer(new Slot(invTicket, 0, 50, 35));
		this.addSlotToContainer(new Slot(invTicket, 1, 111, 35));
	}

}
