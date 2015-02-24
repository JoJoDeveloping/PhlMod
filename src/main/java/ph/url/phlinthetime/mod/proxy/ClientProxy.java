package ph.url.phlinthetime.mod.proxy;

import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.entity.EntityBlackMamba;
import ph.url.phlinthetime.mod.renderer.ItemRenderBlackMamba;
import ph.url.phlinthetime.mod.renderer.ItemRenderKasse;
import ph.url.phlinthetime.mod.renderer.ItemRenderTicketsystem;
import ph.url.phlinthetime.mod.renderer.ItemRenderTrack;
import ph.url.phlinthetime.mod.renderer.RenderBlackMamba;
import ph.url.phlinthetime.mod.renderer.RenderKasse;
import ph.url.phlinthetime.mod.renderer.RenderTicketsystem;
import ph.url.phlinthetime.mod.renderer.RenderTrack;
import ph.url.phlinthetime.mod.tileentity.TileEntityKasse;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import ph.url.phlinthetime.mod.tileentity.TileEntityTrack;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	public void setupRendererAndSound() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrack.class, new RenderTrack());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModPhantasialand.blockTrack), new ItemRenderTrack());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKasse.class, new RenderKasse());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModPhantasialand.blockKasse), new ItemRenderKasse());
	
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTicketsystem.class, new RenderTicketsystem());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModPhantasialand.blockTicketsystem), new ItemRenderTicketsystem());
	}
	
	@Override
	public void setupBeta() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackMamba.class, new RenderBlackMamba());
		MinecraftForgeClient.registerItemRenderer(ModPhantasialand.itemBlackMamba, new ItemRenderBlackMamba());
		
		
		
	}
	
}
