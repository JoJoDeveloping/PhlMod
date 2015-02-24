package ph.url.phlinthetime.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabsPhl extends CreativeTabs {

	public CreativeTabsPhl(String lable) {
		super(lable);
		// TODO Auto-generated constructor stub
	}

	private ItemStack field_151245_t;

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(ModPhantasialand.blockTrack);
	}
	
	@Override
	public ItemStack getIconItemStack() {
        return new ItemStack(ModPhantasialand.blockTrack, 1, 0);
	}

	@Override
	public String getTranslatedTabLabel() {
		return "Phl-Tab";
	}

}
