package ph.url.phlinthetime.mod.items;

import cpw.mods.fml.common.FMLCommonHandler;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlackMamba extends Item {

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer p, World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(w.isRemote)return true;
		EntityBlackMamba e = new EntityBlackMamba(w);
		e.setPosition(x, y, z);
		w.spawnEntityInWorld(e);
		e.setBlockTrackEntityHangs(x, y, z);
		return true;
	}
	
}
