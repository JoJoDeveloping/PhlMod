package ph.url.phlinthetime.mod.items;

import ph.url.phlinthetime.mod.money.MoneyManager;
import ph.url.phlinthetime.mod.money.PlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemCreditCard extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
		if(world.isRemote){
			return is;
		}
		if(is.stackTagCompound == null)is.stackTagCompound = new NBTTagCompound();
		if(!is.stackTagCompound.hasKey("owner"))is.stackTagCompound.setString("owner", player.getUniqueID().toString().replace("-", ""));
		if(!(is.stackTagCompound.getString("owner").equals(player.getUniqueID().toString().replace("-", ""))))
			return new ItemStack(Item.getItemFromBlock(Blocks.dirt));
		if(!is.stackTagCompound.hasKey("created"))is.stackTagCompound.setLong("created", System.currentTimeMillis());
		
		MoneyManager.instance.addNewPlayer(player.getUniqueID().toString().replace("-", ""));
		if(is.stackTagCompound.getLong("created")<MoneyManager.instance.hashmap.get(player.getUniqueID().toString().replace("-", "")).getLastUnlock()){
			is.stackSize = 0;
			return new ItemStack(Item.getItemFromBlock(Blocks.dirt));
		}
		if(MoneyManager.instance.hashmap.get(player.getUniqueID().toString().replace("-", "")).isLocked())
			return new ItemStack(Item.getItemFromBlock(Blocks.dirt));
		
		PlayerInfo pi = ((PlayerInfo)(MoneyManager.instance.hashmap.get(player.getUniqueID().toString().replace("-", ""))));
			player.addChatMessage(new ChatComponentText("Ihr aktuelles Guthaben beträgt: "+pi.getMoney()+" Euro."));
		//player.addChatMessage(new ChatComponentText("Ihre UUID ist: "+player.getUniqueID().toString()));
		return is;
	}
	
	
	
}
