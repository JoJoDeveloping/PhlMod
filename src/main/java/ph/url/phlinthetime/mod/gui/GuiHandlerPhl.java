package ph.url.phlinthetime.mod.gui;

import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerPhl implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		 TileEntity tileEntity = world.getTileEntity(x, y, z);
         if(tileEntity instanceof TileEntityKasse){
        	 if(ID==0)return new ContainerKasse(player.inventory, (TileEntityKasse) tileEntity);
        	 if(ID==1)return new ContainerEditKasse(player.inventory, (TileEntityKasse) tileEntity);
         }
         if(tileEntity instanceof TileEntityTicketsystem && ID == 2){
        	 return new ContainerTS(player.inventory, (TileEntityTicketsystem) tileEntity);
         }
         return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {;
		TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof TileEntityKasse){
            if(ID==0)return new GuiKasse(player.inventory, (TileEntityKasse) tileEntity);
            if(ID==1)return new GuiEditKasse(player.inventory, (TileEntityKasse)tileEntity);
        }
        if(tileEntity instanceof TileEntityTicketsystem && ID == 2){
        	return new GuiTS(player.inventory, (TileEntityTicketsystem) tileEntity);       	 
        }
        return null;
	}

}
