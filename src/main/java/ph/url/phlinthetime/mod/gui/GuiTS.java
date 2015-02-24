package ph.url.phlinthetime.mod.gui;

import ph.url.phlinthetime.mod.ModPhantasialand;
import ph.url.phlinthetime.mod.tileentity.TileEntityTicketsystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiTS extends GuiContainer {

	public static final ResourceLocation tex = new ResourceLocation(ModPhantasialand.MODID, "textures/gui/ts.png");
	
	public GuiTS(InventoryPlayer invP, TileEntityTicketsystem tileEntity) {
		super(new ContainerTS(invP, tileEntity));
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.xSize = 176;
		this.ySize = 166;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		Minecraft.getMinecraft().renderEngine.bindTexture(tex);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
