package ph.url.phlinthetime.mod.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemTicket extends Item {
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(is, par2EntityPlayer, par3List, par4);
		if(is.hasTagCompound()&&is.stackTagCompound.hasKey("entwertet")&&is.stackTagCompound.getBoolean("entwertet")){
			par3List.add("Entwertet");
		}else if(!(is.hasTagCompound()&&is.stackTagCompound.hasKey("entwertet"))){
			par3List.add("Defekt");
		}
	}
	
	@Override
	public boolean getHasSubtypes() {
		return true;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		// TODO Auto-generated method stub
		ItemStack is = new ItemStack(this, 1, 0);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setBoolean("entwertet", false);
		list.add(is);
		is = new ItemStack(this, 1, 0);
		is.stackTagCompound = new NBTTagCompound();
		is.stackTagCompound.setBoolean("entwertet", true);
		list.add(is);
	}
	
}
